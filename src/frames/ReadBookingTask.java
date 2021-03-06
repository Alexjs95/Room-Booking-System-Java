package frames;

import dbtools.Rows;
import javafx.collections.ObservableList;

import java.io.DataInputStream;
import java.net.Socket;

public class ReadBookingTask implements Runnable {
    Socket socket;
    BookingController client;
    DataInputStream input;

    // Constructor
    public ReadBookingTask(Socket socket, BookingController client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {      // infinite loop for listening.
            try {
                input = new DataInputStream(socket.getInputStream());       // new input
                String msg = input.readUTF();       // get message from input sent from client.
                System.out.println(msg);
                if (msg.equals("RefreshTable")) {   // check the contents of message, new booking so reset tableview.
                    client.tableView.setItems(null);
                    ObservableList<Rows> data;
                    TableData tableData = new TableData();
                    data = tableData.getData();
                    client.resetForm();
                    client.setTableView(data);

                }
            } catch (Exception ex) {
                System.out.println("error reading from server");
                ex.printStackTrace();
                break;
            }
        }
    }
}
