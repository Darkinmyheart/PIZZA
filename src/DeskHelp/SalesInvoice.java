package DeskHelp;

import UI.HoaDonJframe;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import javax.swing.text.StyleConstants;

public class SalesInvoice {




    public void printInvoice(JPanel invoicePanel,Integer paperWidth,Integer paperHeight) {
        PrinterJob job = PrinterJob.getPrinterJob();

        PageFormat pageFormat = job.defaultPage();
        Paper paper = new Paper();


        double marginLeft = 0.0;
        double marginRight = 0.0;
        double marginTop = 0.0;
        double marginBottom = 0.0;

        paper.setSize(paperWidth, paperHeight);
        paper.setImageableArea(marginLeft, marginTop, paperWidth - marginLeft - marginRight, paperHeight - marginTop - marginBottom);

        pageFormat.setPaper(paper);

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                invoicePanel.setSize((int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());
                invoicePanel.printAll(graphics);

                return PAGE_EXISTS;
            }
        }, pageFormat);

        boolean doPrint = job.printDialog();
        int check = 0;
        if (doPrint) {
            try {
                check = 1;
                job.print();
            } catch (PrinterException e) {
                // Xử lý ngoại lệ in
                e.printStackTrace();
            }
        }else{
           JOptionPane.showMessageDialog( new HoaDonJframe(),"Chua in");
        }
        HoaDonJframe.CheckInt(check);
    }
}

