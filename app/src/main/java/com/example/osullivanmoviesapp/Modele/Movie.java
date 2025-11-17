package com.example.osullivanmoviesapp.Modele;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Movie {

    // ========== ATTRIBUTS DE BASE ==========

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private float voteAverage;

    // ========== NOUVEAUX ATTRIBUTS ==========

    /**
     * Backdrop : Image horizontale pour l'affichage en plein écran
     * Format : "/abc123.jpg"
     */
    @SerializedName("backdrop_path")
    private String backdropPath;

    /**
     * Date de sortie du film
     * Format : "2024-03-15"
     */
    @SerializedName("release_date")
    private String releaseDate;

    /**
     * Langue originale du film
     * Format : "en", "fr", "es", etc.
     */
    @SerializedName("original_language")
    private String originalLanguage;

    /**
     * Titre original (dans la langue d'origine)
     * Exemple : "The Godfather" même si traduit en "Le Parrain"
     */
    @SerializedName("original_title")
    private String originalTitle;

    /**
     * Nombre de votes reçus
     * Utile pour afficher "basé sur X votes"
     */
    @SerializedName("vote_count")
    private int voteCount;

    /**
     * Popularité du film (score calculé par TMDB)
     * Plus c'est élevé, plus le film est populaire
     */
    @SerializedName("popularity")
    private float popularity;

    /**
     * Si le film est destiné aux adultes (18+)
     * true = contenu adulte, false = tout public
     */
    @SerializedName("adult")
    private boolean adult;

    /**
     * Liste des IDs de genres
     * Exemple : [28, 12] = Action + Aventure
     * Note : Pour les noms, il faut un appel API séparé
     */
    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    /**
     * Durée du film en minutes
     * Disponible uniquement dans l'endpoint /movie/{id}
     */
    @SerializedName("runtime")
    private Integer runtime;

    /**
     * Budget du film en dollars
     * Disponible uniquement dans l'endpoint /movie/{id}
     */
    @SerializedName("budget")
    private long budget;

    /**
     * Revenus générés en dollars
     * Disponible uniquement dans l'endpoint /movie/{id}
     */
    @SerializedName("revenue")
    private long revenue;

    /**
     * Tagline (phrase d'accroche) du film
     * Exemple : "In space, no one can hear you scream"
     */
    @SerializedName("tagline")
    private String tagline;

    /**
     * Statut du film
     * Exemples : "Released", "Post Production", "In Production"
     */
    @SerializedName("status")
    private String status;

    // ========== GETTERS DE BASE ==========

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    // ========== NOUVEAUX GETTERS ==========

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public float getPopularity() {
        return popularity;
    }

    public boolean isAdult() {
        return adult;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public long getBudget() {
        return budget;
    }

    public long getRevenue() {
        return revenue;
    }

    public String getTagline() {
        return tagline;
    }

    public String getStatus() {
        return status;
    }

    // ========== MÉTHODES UTILITAIRES ==========

    /**
     * Retourne la durée formatée (ex: "2h 15min")
     */
    public String getFormattedRuntime() {
        if (runtime == null || runtime <= 0) {
            return "Durée inconnue";
        }
        int hours = runtime / 60;
        int minutes = runtime % 60;

        if (hours > 0) {
            return hours + "h " + minutes + "min";
        } else {
            return minutes + "min";
        }
    }

    /**
     * Retourne l'année de sortie (extrait de release_date)
     */
    public String getReleaseYear() {
        if (releaseDate == null || releaseDate.isEmpty()) {
            return "Année inconnue";
        }
        // Format de releaseDate : "2024-03-15"
        return releaseDate.split("-")[0];
    }

    /**
     * Retourne la note formatée avec 1 décimale
     */
    public String getFormattedRating() {
        return String.format("%.1f", voteAverage);
    }

    /**
     * Retourne le nombre de votes formaté
     * Exemple : 1234 -> "1.2K", 1500000 -> "1.5M"
     */
    public String getFormattedVoteCount() {
        if (voteCount >= 1_000_000) {
            return String.format("%.1fM", voteCount / 1_000_000.0);
        } else if (voteCount >= 1_000) {
            return String.format("%.1fK", voteCount / 1_000.0);
        } else {
            return String.valueOf(voteCount);
        }
    }

    /**
     * Vérifie si le film a une image backdrop
     */
    public boolean hasBackdrop() {
        return backdropPath != null && !backdropPath.isEmpty();
    }

    /**
     * Vérifie si le film a une image poster
     */
    public boolean hasPoster() {
        return posterPath != null && !posterPath.isEmpty();
    }

    /**
     * Retourne l'URL complète de l'image backdrop
     * @param size Taille souhaitée (w300, w780, w1280, original)
     */
    public String getBackdropUrl(String size) {
        if (!hasBackdrop()) {
            return null;
        }
        return "https://image.tmdb.org/t/p/" + size + backdropPath;
    }

    /**
     * Retourne l'URL complète de l'image poster
     * @param size Taille souhaitée (w185, w342, w500, w780, original)
     */
    public String getPosterUrl(String size) {
        if (!hasPoster()) {
            return null;
        }
        return "https://image.tmdb.org/t/p/" + size + posterPath;
    }

    // ========== MÉTHODE toString() POUR DEBUG ==========

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", runtime=" + runtime +
                '}';
    }
}