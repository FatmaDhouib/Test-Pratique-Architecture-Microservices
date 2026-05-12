import 'package:flutter/material.dart';
import '../models/categorie.dart';
import '../models/produit.dart';
import '../services/api_service.dart';
import 'avis_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  Categorie? _selectedCategorie;
  late Future<List<Categorie>> _categories;
  Future<List<Produit>>? _produits;

  @override
  void initState() {
    super.initState();
    _categories = ApiService.getCategories();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('🛒 Boutique en ligne'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),
      body: Column(
        children: [
          // Sélecteur de catégorie
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: FutureBuilder<List<Categorie>>(
              future: _categories,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const CircularProgressIndicator();
                }
                if (snapshot.hasError) {
                  return Text('Erreur: ${snapshot.error}');
                }
                return DropdownButton<Categorie>(
                  hint: const Text('Sélectionner une catégorie'),
                  value: _selectedCategorie,
                  isExpanded: true,
                  items: snapshot.data!.map((cat) {
                    return DropdownMenuItem(
                      value: cat,
                      child: Text(cat.nom),
                    );
                  }).toList(),
                  onChanged: (cat) {
                    setState(() {
                      _selectedCategorie = cat;
                      _produits = ApiService.getProduitsByCategorie(cat!.id);
                    });
                  },
                );
              },
            ),
          ),

          // Liste des produits
          Expanded(
            child: _produits == null
                ? const Center(child: Text('Sélectionnez une catégorie'))
                : FutureBuilder<List<Produit>>(
                    future: _produits,
                    builder: (context, snapshot) {
                      if (snapshot.connectionState == ConnectionState.waiting) {
                        return const Center(child: CircularProgressIndicator());
                      }
                      if (snapshot.hasError) {
                        return Center(child: Text('Erreur: ${snapshot.error}'));
                      }
                      if (snapshot.data!.isEmpty) {
                        return const Center(child: Text('Aucun produit'));
                      }
                      return ListView.builder(
                        itemCount: snapshot.data!.length,
                        itemBuilder: (context, index) {
                          final produit = snapshot.data![index];
                          return ListTile(
                            leading: const Icon(Icons.shopping_bag),
                            title: Text(produit.nom),
                            subtitle: Text('${produit.prix} € · Stock: ${produit.stock}'),
                            trailing: const Icon(Icons.arrow_forward_ios),
                            onTap: () {
                              // Aller vers les avis de ce produit
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (_) => AvisScreen(produit: produit),
                                ),
                              );
                            },
                          );
                        },
                      );
                    },
                  ),
          ),
        ],
      ),
    );
  }
}