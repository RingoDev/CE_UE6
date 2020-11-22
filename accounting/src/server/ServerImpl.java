package server;

import shared.Order;
import shared.OrderServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.rmi.RemoteException;

public class ServerImpl implements OrderServer {

    private BigInteger orderId;
    private String handlebarType;
    private String handlebarMaterial;
    private String handlebarGearshift;
    private String handleMaterial;

    @Override
    public void sendOrder(Order o) throws RemoteException {

        orderId = o.getOrderId();
        handlebarType = o.getHandlebarType();
        handlebarMaterial = o.getHandlebarMaterial();
        handlebarGearshift = o.getHandlebarGearshift();
        handleMaterial = o.getHandleMaterial();

        String s = "Bestellung " + orderId + ": "
                    + handlebarType + ", "
                    + handleMaterial + ", "
                    + handlebarGearshift + ", "
                    + handleMaterial;

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
            writer.println(s);
            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
