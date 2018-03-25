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
        double juros;
        int meses;
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("juros") != null && !"".equals(request.getParameter("juros"))) {
                juros = Double.parseDouble(request.getParameter("juros")) / 100;
            } else {
                juros = 0.01;
            }

            if (request.getParameter("meses") != null && !"".equals(request.getParameter("meses"))) {
                meses = Integer.parseInt(request.getParameter("meses"));
            } else {
                meses = 12;
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Juros Simples</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Juros Simples: Tabela de Valores</h1>");
            out.println("<form>");
            out.println("<label>Valor do Juros: </label><input type=\"text\" name=\"juros\"><br><br>");
            out.println("<label>Número de Meses: </label><input type=\"text\" name=\"meses\"><br>");
            out.println("<button type=\"submit\">Calcular</button>");
            out.println("</form>");
            out.println("<h4>" + constroiTabela(juros, meses) + "</h4>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String constroiTabela(Double juros, int numeroMeses) {
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
        for (int i = 0; i <= numeroMeses; i++) {
            LocalDate data = atual.plusMonths(i);
            tabela.append("<tr style='border: 1px solid black;'>");
            tabela.append("<td style='border: 1px solid black;'>").append(data.format(formatter)).append("</td>");
            tabela.append("<td style='border: 1px solid black;'>").append(calculaValor(1000, i, juros, 0.5)).append("</td>");
            tabela.append("<td style='border: 1px solid black;'>").append(calculaValor(1000, i, juros, 1)).append("</td>");
            tabela.append("<td style='border: 1px solid black;'>").append(calculaValor(1000, i, juros, 1.5)).append("</td>");
            tabela.append("</tr>");
        }
        tabela.append("</table");
        return tabela.toString();

    }

    private String calculaValor(double valor, int mes, double juros, double valorInter) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valor * (1 + mes * (valorInter * juros)));
    }

}
