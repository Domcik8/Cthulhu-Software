import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.inject.Inject;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
   
    @Inject
    DatabaseManager dbm;
            
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String imageId = String.valueOf(request.getPathInfo().substring(1)); // Gets string that goes after "/images/".

        Houseimage image = (Houseimage)dbm.getEntity("Houseimage", "Internalname", imageId);
        if(image == null) return;
        
        response.setHeader("Content-Type", image.getMimetype());
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getInternalname() + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new ByteArrayInputStream((byte[])image.getImage())); // Creates buffered input stream.
            output = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            for (int length = 0; (length = input.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }
        } finally {
            if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
            if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
        }
    }
}