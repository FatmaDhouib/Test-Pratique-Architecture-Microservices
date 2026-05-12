import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/categorie.dart';
import '../models/produit.dart';
import '../models/avis.dart';

class ApiService {

  static const String baseUrl = 'http://localhost:8090';

  // Récupérer toutes les catégories
  static Future<List<Categorie>> getCategories() async {
    final response = await http.get(Uri.parse('$baseUrl/api/categories'));
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Categorie.fromJson(json)).toList();
    }
    throw Exception('Erreur lors du chargement des catégories');
  }

  // Récupérer les produits d'une catégorie
  static Future<List<Produit>> getProduitsByCategorie(int categorieId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/api/produits?categorieId=$categorieId'),
    );
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Produit.fromJson(json)).toList();
    }
    throw Exception('Erreur lors du chargement des produits');
  }

  // Récupérer les avis d'un produit
  static Future<List<Avis>> getAvisByProduit(int produitId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/api/avis/$produitId'),
    );
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((json) => Avis.fromJson(json)).toList();
    }
    throw Exception('Erreur lors du chargement des avis');
  }
}