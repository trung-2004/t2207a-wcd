package wcd.jpa.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import wcd.jpa.entities.Classes;
import wcd.jpa.entities.Student;
import wcd.jpa.entities.Subject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@WebServlet("/create-students")
public class StudentCreateController extends HttpServlet {
    private SessionFactory sessionFactory;
    @Override
    public void init() throws ServletException {
        try{
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml").buildSessionFactory();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Classes> list = session.createQuery("FROM Classes", Classes.class)
                    .getResultList();
            List<Subject> listSubject = session.createQuery("FROM Subject ", Subject.class)
                    .getResultList();
            session.getTransaction().commit();
            req.setAttribute("classes",list);
            req.setAttribute("subjects",listSubject);
        }
        req.getRequestDispatcher("student/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();
        student.setName(req.getParameter("name"));
        student.setEmail(req.getParameter("email"));
        student.setAddress(req.getParameter("address"));
        String classId = req.getParameter("class_id");
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Classes aClass = session.get(Classes.class, Integer.parseInt(classId));
            if (aClass == null) {
                return;
            }
            student.setClasses(aClass);

            // find subjects
            List<Integer> s_ids = Arrays.stream(req.getParameterValues("subject_id[]"))
                    .map(Integer::parseInt).collect(Collectors.toList());
            Query<Subject> query = session.createQuery("FROM Subject WHERE id IN (:ids)", Subject.class);
            query.setParameter("ids", s_ids);
            List<Subject> subjects = query.getResultList();
            student.setSubjects(subjects);

            session.save(student);
            session.getTransaction().commit();

            // send email to student
            String senderEmail = "hoangtulaubar@gmail.com";
            String passwordEmail = "eeeinaobsqfytgra";

            // config connect
            Properties prop = new Properties();
            prop.put("mail.smtp.auth","true");
            prop.put("mail.smtp.ssl.protocols","TLSv1.2");
            prop.put("mail.smtp.ssl.trust","smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable","true");
            prop.put("mail.smtp.host","smtp.gmail.com");
            prop.put("mail.smtp.port","587");

            javax.mail.Session mailSession = javax.mail.Session.getInstance(prop,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail,passwordEmail);
                        }
                    }
            );
            try{
                Message message = new MimeMessage(mailSession);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipient(Message.RecipientType.TO,
                        new InternetAddress("trungtvt.dev@gmail.com"));
                message.setSubject("Create new a student!");

                Multipart multipart = new MimeMultipart("related");
                BodyPart messageBodyPart = new MimeBodyPart();
                String htmlText = GetHtmlcontentRegisterStudent(student.name, student.email, student.address, student.id);
                messageBodyPart.setContent(htmlText, "text/html; charset=UTF-8");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);

                // gui email
                Transport.send(message);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        resp.sendRedirect("list-student");
    }
    private static String GetHtmlcontentRegisterStudent(String name, String email, String password, int id)
    {
        String Response = "<!doctype html><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><title>Examonimy</title><!--[if !mso]><!-- --><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><style type=\"text/css\">#outlook a{padding:0}body{margin:0;padding:0;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%}table,td{border-collapse:collapse;mso-table-lspace:0pt;mso-table-rspace:0pt}img{border:0;height:auto;line-height:100%;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic}p{display:block;margin:13px 0}</style><!--[if mso]> <xml> <o:OfficeDocumentSettings> <o:AllowPNG/> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml> <![endif]--><!--[if lte mso 11]><style type=\"text/css\">.mj-outlook-group-fix{width:100% !important}</style><![endif]--><!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,700\" rel=\"stylesheet\" type=\"text/css\"><style type=\"text/css\">@import url(https://fonts.googleapis.com/css?family=Montserrat:400,700);</style><!--<![endif]--><style type=\"text/css\">@media only screen and (min-width:400px){.mj-column-per-100{width:100% !important;max-width:100%}}</style><style type=\"text/css\">@media only screen and (max-width:400px){table.mj-full-width-mobile{width:100% !important}td.mj-full-width-mobile{width:auto !important}}</style></head><body style=\"background-color:#F7FCFF;\"><div style=\"display:none;font-size:1px;color:#ffffff;line-height:1px;max-height:0px;max-width:0px;opacity:0;overflow:hidden;\">Automatic email sent by the Contactlab Marketing Cloud platform. Please, don&#x27;t reply.</div><div style=\"background-color:#F7FCFF;\"><!--[if mso | IE]><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:460px;\" width=\"460\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"margin:0px auto;max-width:460px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:20px 0;padding-bottom:0px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" width=\"460px\" ><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:460px;\" width=\"460\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"background:#F7FCFF;background-color:#F7FCFF;margin:0px auto;max-width:460px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#F7FCFF;background-color:#F7FCFF;width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:20px 0;padding-bottom:25px;padding-left:10px;padding-right:10px;padding-top:0px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:440px;\" ><![endif]--><div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\"><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;\" class=\"mj-full-width-mobile\"><tbody><tr><td style=\"width:300px;\" class=\"mj-full-width-mobile\"><a href=\"http://mc.contactlab.it\" target=\"_blank\"><img alt=\"Contactlab Marketing Cloud logo\" height=\"auto\" src=\"https://i.postimg.cc/zvdjMxkG/logo-mc-full-positive-593x60.png\" style=\"border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:13px;\" title=\"Contactlab Marketing Cloud\" width=\"300\"></a></td></tr></tbody></table></td></tr></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body-section-outlook\" style=\"width:460px;\" width=\"460\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div class=\"body-section\" style=\"-webkit-box-shadow: 0 1px 3px 0 rgba(0, 20, 32, 0.12); -moz-box-shadow: 0 1px 3px 0 rgba(0, 20, 32, 0.12); box-shadow: 0 1px 3px 0 rgba(0, 20, 32, 0.12); margin: 0px auto; max-width: 460px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:0px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" width=\"460px\" ><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:460px;\" width=\"460\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"background:#FFFFFF;background-color:#FFFFFF;margin:0px auto;border-radius:8px;max-width:460px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#FFFFFF;background-color:#FFFFFF;width:100%;border-radius:8px;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:20px 0;padding-bottom:25px;padding-left:10px;padding-right:10px;padding-top:25px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:440px;\" ><![endif]--><div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\"><tr><td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:30px;font-weight:700;line-height:36px;text-align:left;color:#1D3344;\">Hi " + name + ",</div></td></tr><tr><td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:21px;text-align:left;color:#001420;\">your Examonimy account has been created! 🎉</div></td></tr><tr><td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:21px;text-align:left;color:#001420;\">Find below your credentials.</div></td></tr><tr><td style=\"font-size:0px;padding:20px 0;padding-top:10px;padding-right:25px;padding-bottom:10px;padding-left:25px;word-break:break-word;\"><!--[if mso | IE]><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:440px;\" width=\"440\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"background:#F7FCFF;background-color:#F7FCFF;margin:0px auto;max-width:440px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#F7FCFF;background-color:#F7FCFF;width:100%;\"><tbody><tr><td style=\"border-left:4px solid #0391EC;direction:ltr;font-size:0px;padding:20px 0;padding-bottom:10px;padding-left:25px;padding-right:25px;padding-top:10px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:386px;\" ><![endif]--><div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\"><tr><td align=\"left\" style=\"font-size:0px;padding:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:12px;font-weight:400;line-height:16px;text-align:left;color:#5B768C;\">Email:</div></td></tr><tr><td align=\"left\" style=\"font-size:0px;padding:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:14px;font-weight:700;line-height:21px;text-align:left;color:#001420;\">" + email + "</div></td></tr></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr><tr><td style=\"font-size:0px;padding:20px 0;padding-top:10px;padding-right:25px;padding-bottom:10px;padding-left:25px;word-break:break-word;\"><!--[if mso | IE]><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:440px;\" width=\"440\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"background:#F7FCFF;background-color:#F7FCFF;margin:0px auto;max-width:440px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#F7FCFF;background-color:#F7FCFF;width:100%;\"><tbody><tr><td style=\"border-left:4px solid #0391EC;direction:ltr;font-size:0px;padding:20px 0;padding-bottom:10px;padding-left:25px;padding-right:25px;padding-top:10px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:386px;\" ><![endif]--><div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\"><tr><td align=\"left\" style=\"font-size:0px;padding:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:12px;font-weight:400;line-height:16px;text-align:left;color:#5B768C;\">Password:</div></td></tr><tr><td align=\"left\" style=\"font-size:0px;padding:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:14px;font-weight:700;line-height:21px;text-align:left;color:#001420;\">" + password + "</div></td></tr></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr><tr><td align=\"center\" vertical-align=\"middle\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:separate;width:200px;line-height:100%;\"><tr><td align=\"center\" bgcolor=\"#0391EC\" role=\"presentation\" style=\"border:none;border-radius:8px;cursor:auto;mso-padding-alt:10px 25px;background:#0391EC;\" valign=\"middle\"><a href=\"http://localhost:8080/T2207A_WCD1_war_exploded/add-password?id="+ id +"\" style=\"display:inline-block;width:150px;background:#0391EC;color:#FFFFFF;font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:21px;margin:0;text-decoration:none;text-transform:none;padding:10px 25px;mso-padding-alt:0px;border-radius:8px;\" target=\"_blank\">Sign In</a></td></tr></table></td></tr><tr><td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:21px;text-align:left;color:#001420;\">For security reasons, please change the temporary password and keep the new one.</div></td></tr><tr><td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:21px;text-align:left;color:#001420;\">Thank you, the Examonimy team.</div></td></tr></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:460px;\" width=\"460\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"margin:0px auto;max-width:460px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:20px 0;padding-top:8px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" width=\"460px\" ><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" style=\"width:460px;\" width=\"460\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]--><div style=\"background:#F7FCFF;background-color:#F7FCFF;margin:0px auto;max-width:460px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#F7FCFF;background-color:#F7FCFF;width:100%;\"><tbody><tr><td style=\"direction:ltr;font-size:0px;padding:20px 0;padding-bottom:25px;padding-left:10px;padding-right:10px;padding-top:25px;text-align:center;\"><!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:440px;\" ><![endif]--><div class=\"mj-column-per-100 mj-outlook-group-fix\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\"><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-top:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:12px;font-weight:400;line-height:16px;text-align:center;color:#5B768C;\">The email is auto generated. Please, don&#x27;t reply.</div></td></tr><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-bottom:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:12px;font-weight:400;line-height:16px;text-align:center;color:#5B768C;\">Any doubts or questions? <a href=\"https://support.contactlab.com/hc/en-us\" style=\"font-weight:400;color:#0391EC;text-decoration:none;font-size:12px;line-height:16px\">Contact us.</a></div></td></tr><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-top:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:12px;font-weight:400;line-height:16px;text-align:center;color:#5B768C;\">Otherwise, consult <a href=\"https://explore.contactlab.com\" style=\"font-weight:400;color:#0391EC;text-decoration:none;font-size:12px;line-height:16px\">the platform documentation.</a></div></td></tr><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-bottom:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:12px;font-weight:400;line-height:16px;text-align:center;color:#5B768C;\">Contactlab Marketing Cloud is a product of <a href=\"https://explore.contactlab.com\" style=\"font-weight:400;color:#0391EC;text-decoration:none;font-size:12px;line-height:16px\">Contactlab S.p.A.</a></div></td></tr><tr><td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-top:0px;word-break:break-word;\"><div style=\"font-family:Montserrat, Helvetica, Arial, sans-serif;font-size:12px;font-weight:400;line-height:16px;text-align:center;color:#5B768C;\">Via Natale Battaglia, 12 - 20127 Milan, Italy</div></td></tr></table></div><!--[if mso | IE]></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table></td></tr></table><![endif]--></td></tr></tbody></table></div><!--[if mso | IE]></td></tr></table><![endif]--></div></body></html>";
        return Response;
    }
}