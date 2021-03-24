// В этом классе (и еще в ClientHandler и BaseAuthService) решение п.1 и п.4 ДЗ 6 урока. Другие классы по чату относительно 3 урока не менялись,
// поэтому в пакет к 6 уроку не добавлены.

package ru.GeekBrains.Rostislav.Java3Group3.lesson2.ServerSide;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
        private static final Logger logger = LogManager.getLogger(MyServer.class);
        private final BufferedReader stopServer = new BufferedReader(new InputStreamReader(System.in)); // для остановки сервера из консоли

        private List<ClientHandler> clients;
        private AuthService authService;

        public AuthService getAuthService() {
            return authService;
        }

        public MyServer() {
            int PORT = 8189;
            try (ServerSocket server = new ServerSocket(PORT)) {
                authService = new BaseAuthService();
                authService.start();
                clients = new ArrayList<>();

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    while (true) {          // реализация потока через ExecutorService
                        try {
                            if(stopServer.readLine().equals("/stop")) {// для остановки сервера из консоли набрать /stop
                                broadcastMsg("Сервер выключен администратором.");
                                authService.stop();
                                executorService.shutdown();     // отключение ExecutorService
                                logger.info("Сервер остановлен.");
                                System.exit(0);
                            }
                        } catch (IOException e) {
                            logger.error("Ошибка в работе сервера.");
                            e.printStackTrace();
                        }
                    }
                });

                while (true) {
                    logger.info("Сервер ожидает подключения.");
                    //System.out.println("Сервер ожидает подключения");
                    Socket socket = server.accept();
                    logger.info("Клиент подключился");
                    //System.out.println("Клиент подключился");
                    new ClientHandler(this, socket);
                }
            } catch (IOException e) {
                logger.error("Ошибка в работе сервера");
                //System.out.println("Ошибка в работе сервера");
            } finally {
                if (authService != null) {
                    authService.stop();
                }
            }
        }

        public synchronized boolean isNickBusy(String nick) {
            for (ClientHandler o : clients) {
                if (o.getName().equals(nick)) {
                    return true;
                }
            }
            return false;
        }

         public synchronized void broadcastClientsList() {
             StringBuilder sb = new StringBuilder("/clients ");
             for (ClientHandler o : clients) {
                 sb.append(o.getName() + " ");
             }
             broadcastMsg(sb.toString());
         }

         public synchronized void broadcastMsg(String msg) {
                 for (ClientHandler o : clients) {
                         o.sendMsg(msg);
                     }
             }
        public synchronized void toOneMsg(ClientHandler from, String nick, String msg) {     // персональное сообщение
            for (ClientHandler o : clients) {
                if(o.getName().equals(nick) && !nick.equals("")) {
                    o.sendMsg("от " + from.getName() + ": " + msg);
                    from.sendMsg("клиенту " + nick + ": " + msg);
                    return;
                }
            }
            from.sendMsg("Участника с ником " + nick + " нет в чат-комнате");
        }

        public synchronized void unsubscribe(ClientHandler o) {
            clients.remove(o);
            broadcastClientsList();
        }

        public synchronized void subscribe(ClientHandler o) {
            clients.add(o);
            broadcastClientsList();
        }
}