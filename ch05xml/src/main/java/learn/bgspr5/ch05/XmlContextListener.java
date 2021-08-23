package learn.bgspr5.ch05;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class XmlContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = buildXmlContext(sce.getServletContext());
        sce.getServletContext().setAttribute("context", context);
    }

    private ApplicationContext buildXmlContext(ServletContext servletContext) {
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setServletContext(servletContext);
        context.refresh();
        return context;
    }
}
