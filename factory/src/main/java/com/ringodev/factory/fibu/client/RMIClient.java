package com.ringodev.factory.fibu.client;
import com.ringodev.factory.fibu.shared.OrderServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

        private OrderServer server;

        public void startClient() throws RemoteException, NotBoundException {
            //connection to server
            final Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            //search server
            server = (OrderServer) registry.lookup("OrderServer");
        }

        public OrderServer getServer() {
            return server;
        }
}
