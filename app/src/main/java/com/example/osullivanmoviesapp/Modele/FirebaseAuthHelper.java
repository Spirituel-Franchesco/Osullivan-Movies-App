package com.example.osullivanmoviesapp.Modele;

import android.app.Activity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAuthHelper {

    // Instance de FirebaseAuth - c'est l'objet principal pour gérer l'authentification
    private FirebaseAuth mAuth;

    // Instance de Firestore - pour stocker des données supplémentaires des utilisateurs
    private FirebaseFirestore db;

    // Contexte de l'activité appelante (pour afficher des messages Toast)
    private Activity activity;

    /**
     * Constructeur
     *
     * @param activity L'activité qui utilise cette classe
     */
    public FirebaseAuthHelper(Activity activity) {
        this.activity = activity;
        // Récupère l'instance unique de FirebaseAuth (Pattern Singleton)
        this.mAuth = FirebaseAuth.getInstance();
        // Récupère l'instance unique de Firestore
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * INSCRIPTION - Crée un nouveau compte utilisateur
     * <p>
     * FONCTIONNEMENT :
     * 1. Firebase crée le compte avec email/password
     * 2. Si succès, on stocke les infos supplémentaires dans Firestore
     * 3. On appelle le callback pour informer l'Activity du résultat
     *
     * @param email     Email de l'utilisateur
     * @param password  Mot de passe (Firebase va le hasher automatiquement avec bcrypt)
     * @param firstName Prénom (donnée supplémentaire)
     * @param lastName  Nom (donnée supplémentaire)
     * @param callback  Interface pour renvoyer le résultat (succès/échec)
     */
    public void registerUser(String email, String password, String firstName, String lastName,
                             AuthCallback callback) {

        // Méthode asynchrone de Firebase pour créer un compte
        // "addOnCompleteListener" = quand l'opération est terminée (succès ou échec)
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        // Succès ! L'utilisateur est créé
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            // On stocke les infos supplémentaires dans Firestore
                            saveUserDataToFirestore(user.getUid(), email, firstName, lastName);
                            callback.onSuccess("Inscription réussie !");
                        }
                    } else {
                        // Échec - on récupère le message d'erreur
                        String errorMessage = task.getException() != null
                                ? task.getException().getMessage()
                                : "Erreur inconnue";
                        callback.onFailure(errorMessage);
                    }
                });
    }


    /**
     * CONNEXION - Authentifie un utilisateur existant
     *
     * FONCTIONNEMENT :
     * Firebase compare automatiquement le hash du mot de passe entré
     * avec celui stocké dans sa base de données sécurisée
     *
     * @param email Email de l'utilisateur
     * @param password Mot de passe
     * @param callback Interface pour renvoyer le résultat
     */
    public void loginUser(String email, String password, AuthCallback callback) {

        // Méthode asynchrone pour se connecter
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        // Connexion réussie
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            callback.onSuccess("Connexion réussie !");
                        }
                    } else {
                        // Échec de connexion
                        String errorMessage = task.getException() != null
                                ? task.getException().getMessage()
                                : "Email ou mot de passe incorrect";
                        callback.onFailure(errorMessage);
                    }
                });
    }

    /**
     * DÉCONNEXION - Déconnecte l'utilisateur actuel
     */
    public void logoutUser() {
        mAuth.signOut();
    }

    /**
     * Vérifie si un utilisateur est actuellement connecté
     * @return true si connecté, false sinon
     */
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    /**
     * Récupère l'utilisateur actuellement connecté
     * @return FirebaseUser ou null si déconnecté
     */
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Sauvegarde les données supplémentaires de l'utilisateur dans Firestore
     *
     * POURQUOI FIRESTORE EN PLUS DE AUTH ?
     * Firebase Auth stocke uniquement : email, password (hashé), UID
     * Firestore nous permet de stocker : prénom, nom, scores, préférences, etc.
     *
     * STRUCTURE FIRESTORE :
     * Collection "users"
     *   └── Document avec UID de l'utilisateur
     *       ├── email
     *       ├── firstName
     *       ├── lastName
     *       └── createdAt
     *
     * @param uid Identifiant unique Firebase de l'utilisateur
     * @param email Email
     * @param firstName Prénom
     * @param lastName Nom
     */

    private void saveUserDataToFirestore(String uid, String email, String firstName, String lastName) {

        // Map = structure clé-valeur (comme un JSON)
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        userData.put("createdAt", System.currentTimeMillis()); // Timestamp de création

        // Sauvegarde dans Firestore
        // Collection "users" → Document avec l'UID comme ID
        db.collection("users")
                .document(uid)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    // Données sauvegardées avec succès
                    Toast.makeText(activity, "Profil créé !", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Erreur lors de la sauvegarde
                    Toast.makeText(activity, "Erreur de sauvegarde du profil", Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * RÉINITIALISATION DE MOT DE PASSE
     * Firebase envoie automatiquement un email à l'utilisateur
     *
     * @param email Email de l'utilisateur
     * @param callback Interface pour renvoyer le résultat
     */
    public void resetPassword(String email, AuthCallback callback) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess("Email de réinitialisation envoyé !");
                    } else {
                        String errorMessage = task.getException() != null
                                ? task.getException().getMessage()
                                : "Erreur d'envoi d'email";
                        callback.onFailure(errorMessage);
                    }
                });
    }

    /**
     * INTERFACE CALLBACK
     *
     * CONCEPT : Les opérations Firebase sont ASYNCHRONES
     * Ça veut dire qu'elles ne se terminent pas instantanément
     * On utilise des callbacks pour être notifié quand c'est terminé
     *
     * C'est comme commander une pizza :
     * - Tu appelles (lances l'opération)
     * - Tu continues à faire autre chose (l'app reste utilisable)
     * - On te rappelle quand c'est prêt (callback)
     */
    public interface AuthCallback {
        void onSuccess(String message);
        void onFailure(String errorMessage);
    }
}

