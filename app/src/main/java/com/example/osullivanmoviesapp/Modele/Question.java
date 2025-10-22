package com.example.osullivanmoviesapp.Modele;

public class Question {

    private int question;
    private int reponse1;
    private int reponse2;
    private int reponse3;
    private int reponse4;
    private int reponseCorrecte;

    public Question(int question, int reponse1, int reponse2, int reponse3, int reponse4, int reponseCorrecte) {
        this.question = question;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponse3 = reponse3;
        this.reponse4 = reponse4;
        this.reponseCorrecte = reponseCorrecte;
    }

    public int getQuestion() {
        return question;
    }

    public int getReponse1() {
        return reponse1;
    }

    public int getReponse2() {
        return reponse2;
    }

    public int getReponse3() {
        return reponse3;
    }

    public int getReponse4() {
        return reponse4;
    }

    public int getReponseCorrecte() {
        return reponseCorrecte;
    }
}
