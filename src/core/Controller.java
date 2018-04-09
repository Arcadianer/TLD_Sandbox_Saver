package core;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Controller {
    @FXML
    private ChoiceBox save_select;

    @FXML
    private ChoiceBox load_select;

    @FXML
    private Label savelabel;


private File save_file;
    private File load_file;
private File[] sandbox_saves_hinterland;
private File[] sandbox_saves_local;

public Controller(){
    File save_file_list=new File(System.getProperty("user.home")+"\\AppData\\Local\\Hinterland\\TheLongDark\\");
     sandbox_saves_hinterland =save_file_list.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith("sandbox");
        }
    });
    System.out.println(save_file_list.getAbsoluteFile());
    save_file_list=new File("saves\\");

    sandbox_saves_local=save_file_list.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith("sandbox");
        }
    });

    System.out.println(save_file_list.getAbsoluteFile());

    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            if (sandbox_saves_hinterland != null) {
                for (File file : sandbox_saves_hinterland) {
                    save_select.getItems().add(file.getName() + " (game)");
                }
            }

            if (sandbox_saves_local != null) {
            for (File file: sandbox_saves_local) {
                load_select.getItems().add(file.getName() + " (backup)");
            }
            }
            save_select.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    System.out.println(newValue);
                    save_file= sandbox_saves_hinterland[(int)newValue];
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    savelabel.setText(simpleDateFormat.format(save_file.lastModified()));
                }
            });
            load_select.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    System.out.println(newValue);
                    load_file = sandbox_saves_local[(int) newValue];
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    savelabel.setText(simpleDateFormat.format(load_file.lastModified()));
                }
            });
        }

    });

    //save_select.setItems();
}
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    @FXML
    private void load() {
        if (load_file != null) {
            try {
                File dest = new File(System.getProperty("user.home") + "\\AppData\\Local\\Hinterland\\TheLongDark\\" + load_file.getName());
                copyFileUsingStream(load_file, dest);
                JOptionPane.showMessageDialog(null, "Save Loaded");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    private void save() {
        if (save_file != null) {
            try {
                File dest = new File("saves\\" + save_file.getName());
                copyFileUsingStream(save_file, dest);
                JOptionPane.showMessageDialog(null, "Games Saved");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
