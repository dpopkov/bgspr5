package learn.bgspr5.ch05;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.bgspr5.ch03.MusicService;
import learn.bgspr5.ch03.model.Song;
import org.springframework.context.ApplicationContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/songs")
public class GetSongsForArtistServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApplicationContext context = (ApplicationContext) req.getServletContext().getAttribute("context");
        MusicService service = context.getBean(MusicService.class);
        ObjectMapper mapper = new ObjectMapper();

        String artist = req.getParameter("artist");

        if (artist == null) {
            log("Missing data in request: requires artist parameter");
            resp.setStatus(500);
        } else {
            List<Song> data = service.getSongsForArtist(artist);
            resp.setStatus(200);
            final String json = mapper.writeValueAsString(data);
            resp.getWriter().println(json);
        }
    }
}
