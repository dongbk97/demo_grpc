package org.example.signature;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PdfToByteArray {
    public static byte[] PdfToByteArray(String pdfFilePath) throws IOException {
        // Đọc tệp PDF
        PDDocument document = PDDocument.load(new File(pdfFilePath));

        // Tạo mảng byte từ tệp PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
