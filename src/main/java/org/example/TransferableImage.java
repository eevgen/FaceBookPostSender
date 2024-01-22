package org.example;

import javax.tools.Tool;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

public class TransferableImage implements Transferable {
    private File imageFile;

    public TransferableImage(File imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(DataFlavor.imageFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(DataFlavor.imageFlavor)) {
            return Toolkit.getDefaultToolkit().getImage(imageFile.toURI().toURL());
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}