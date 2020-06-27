package interfaces;


import bean.Ouvrage;

public interface DataSender {
    public void send(Ouvrage ouvrage, int newStock);
}