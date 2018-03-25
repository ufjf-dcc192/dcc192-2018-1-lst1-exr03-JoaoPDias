package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "InicialServlet", urlPatterns = {"/index.html"})
public class InicialServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Juros Simples</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Juros Simples: Tabela de Valores</h1>");
            out.println("<h4>" + constroiTabela(1.5, 10.0) + "</h4>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String constroiTabela(Double juros, Double numeroMeses) {
        LocalDate atual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM/YYYY");
        StringBuilder tabela = new StringBuilder();
        tabela.append("<table style='border: 1px solid black;'>"
                + "  <tr style='border: 1px solid black;'>"
                + "    <th style='border: 1px solid black;'>Mês</th>"
                + "    <th style='border: 1px solid black;'>0,5%</th>"
                + "    <th style='border: 1px solid black;'>1,0%</th>"
                + "    <th style='border: 1px solid black;'>1,5%</th>"
                + "  </tr>");
        for (int i = 0; i < numeroMeses; i++) {
            LocalDate data = atual.plusMonths(i);
            tabela.append("<tr style='border: 1px solid black;'>");
            tabela.append("<td style='border: 1px solid black;'>").append(data.format(formatter)).append("</td>");
            tabela.append("<td style='border: 1px solid black;'>").append(calculaValor(1000, i, 0.01, 0.5)).append("</td>");
            tabela.append("<td style='border: 1px solid black;'>").append(calculaValor(1000, i, 0.01, 1)).append("</td>");
            tabela.append("<td style='border: 1px solid black;'>").append(calculaValor(1000, i, 0.01, 1.5)).append("</td>");
            tabela.append("</tr>");
        }
        tabela.append("</table");
        return tabela.toString();

    }
    
    private String calculaValor(double valor, int mes, double juros, double valorInter){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valor * (1+mes*(valorInter*juros)));
    }

}
