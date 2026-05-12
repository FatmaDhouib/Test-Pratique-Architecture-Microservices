import 'package:flutter/material.dart';
import '../models/produit.dart';
import '../models/avis.dart';
import '../services/api_service.dart';

class AvisScreen extends StatelessWidget {
  final Produit produit;

  const AvisScreen({super.key, required this.produit});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(produit.nom),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),
      body: FutureBuilder<List<Avis>>(
        future: ApiService.getAvisByProduit(produit.id),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return Center(child: Text('Erreur: ${snapshot.error}'));
          }
          if (snapshot.data!.isEmpty) {
            return const Center(child: Text('Aucun avis pour ce produit'));
          }
          return ListView.builder(
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              final avis = snapshot.data![index];
              return Card(
                margin: const EdgeInsets.all(8),
                child: ListTile(
                  leading: CircleAvatar(child: Text(avis.auteur[0])),
                  title: Text(avis.auteur),
                  subtitle: Text(avis.commentaire),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Text('${avis.note}'),
                      const Icon(Icons.star, color: Colors.amber),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}