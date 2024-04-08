package lk.ijse.dep12.image_explorer.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.*;

public class MainViewController {
    public TreeView<String> tvExplorer;

    public void initialize() {
        TreeItem<String> rootNode = new TreeItem<>();
        rootNode.setValue("This PC");
        rootNode.setGraphic(getIcon("pc"));
        tvExplorer.setRoot(rootNode);
        rootNode.setExpanded(true);

        for (Path disk : FileSystems.getDefault().getRootDirectories()) {
            TreeItem<String> diskNode = new TreeItem<>(disk.toString());
            diskNode.setGraphic(getIcon("disk"));
            rootNode.getChildren().add(diskNode);
            diskNode.expandedProperty().addListener((observable, previous, current) -> {
                if (!current || !diskNode.getChildren().isEmpty()) return;
                try {
                    try (DirectoryStream<Path> paths = Files.newDirectoryStream(disk)) {
                        for (Path folder : paths) {
                            TreeItem<String> folderNode = new TreeItem<>(folder.getFileName().toString());
                            folderNode.setGraphic(getIcon("folder"));
                            diskNode.getChildren().add(folderNode);
                            folderNode.expandedProperty().addListener((o, p, c) -> {
                                folderNode.setGraphic(getIcon(c ? "folder-open" : "folder"));
                            });
//                            if (Files.isDirectory(folder)) {
//                                addSubDirectories(folder, folderNode);
//                            }
                        }
                    }
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to read the disk").show();
                    e.printStackTrace();
                }
            });
        }
        rootNode.getChildren().getFirst().setExpanded(true);
    }

//    private void addSubDirectories(Path directory, TreeItem<String> parentNode) throws AccessDeniedException {
//        try (DirectoryStream<Path> subPaths = Files.newDirectoryStream(directory)) {
//            for (Path subPath : subPaths) {
//                if (Files.isDirectory(subPath)) {
//                    TreeItem<String> subFolderNode = new TreeItem<>(subPath.getFileName().toString());
//                    subFolderNode.setGraphic(getIcon("folder"));
//                    parentNode.getChildren().add(subFolderNode);
//                    addSubDirectories(subPath, subFolderNode);
//                }
//            }
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Failed to read subdirectories").show();
//            e.printStackTrace();
//        }
//    }

    private ImageView getIcon(String icon) {
        ImageView imageView = new ImageView(switch (icon) {
            case "pc" -> "/icon/computer.png";
            case "disk" -> "/icon/hard-drive.png";
            case "folder" -> "/icon/folder.png";
            case "folder-open" -> "/icon/open-folder.png";
            case null, default -> throw new RuntimeException("Invalid icon");
        });
        imageView.setFitWidth(24);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
