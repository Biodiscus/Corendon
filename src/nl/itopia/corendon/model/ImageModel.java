package nl.itopia.corendon.model;

import nl.itopia.corendon.data.Luggage;
import nl.itopia.corendon.data.Picture;
import nl.itopia.corendon.utils.FileUtil;
import nl.itopia.corendon.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * © 2014, Biodiscus.net robin
 */
public class ImageModel {
    private static ImageModel _default = new ImageModel();
    private final DatabaseManager dbmanager = DatabaseManager.getDefault();

    private final int BUFFER_SIZE = 4096;

    private ImageModel() {

    }

    // http://www.codejava.net/java-se/networking/ftp/upload-files-to-ftp-server-using-urlconnection-class
    /**
     * Uploads an image from the given file, this will be called:
     *
     * random UUID.png/jpg
     *
     * @param file          the image to upload
     * @return the path to the uploaded image
     * @throws IOException
     */
    public String uploadImage(File file) throws IOException {
        // Make a FTP connnection

        String server = "biodiscus.net";
        String user = "corendon%40biodiscus.net";
        String password = "PSckX6R4fl";

        String filePath = file.getPath();

        String randomFileName = FileUtil.randomFileName();
        String extension = FileUtil.getFileExtension(file);

        String uploadPath = randomFileName+"."+extension;

        Log.display(uploadPath);


        String ftpURL = "ftp://%s:%s@%s/%s;type=i";

        ftpURL = String.format(ftpURL, user, password, server, uploadPath);
        Log.display("FtpURL", ftpURL);

        URL url = new URL(ftpURL);

        URLConnection connection = url.openConnection();
        OutputStream output = connection.getOutputStream();
        FileInputStream input = new FileInputStream(filePath);

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        input.close();
        output.close();

        return "http://"+server+"/image_upload/"+uploadPath;
    }

    // Insert the image into the database
    public void insertImage(String path, int luggageID) {
        String query = "INSERT INTO photo (path, luggage_id) VALUES ('"+path+"', '"+luggageID+"')";
        try {
            dbmanager.insertQuery(query);
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }


    public List<Picture> getPicturesFromLuggage(int luggageID) {
        List<Picture> pictures = new ArrayList<>();
        try {
            String sql = "SELECT * FROM photo WHERE luggage_id='"+luggageID+"'";
            ResultSet result = dbmanager.doQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("path");
                Picture picture = new Picture(id, name);
                pictures.add(picture);
            }
        } catch (SQLException e) {
            Log.display("SQLEXCEPTION", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        return pictures;
    }


    public static ImageModel getDefault() {
        return _default;
    }
}
