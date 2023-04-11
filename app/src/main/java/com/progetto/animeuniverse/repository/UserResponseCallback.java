package com.progetto.animeuniverse.repository;

import com.progetto.animeuniverse.User;
//interfaccia che permette di notificare dal repository
// al fragment se l'operazione è andata a buon fine o no
// o se c'è un cambiamento di stato
public interface UserResponseCallback {
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}
