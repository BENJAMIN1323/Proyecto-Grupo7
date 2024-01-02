package s2.proyecto.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import s2.proyecto.model.PromedioGeneral;
import s2.proyecto.repository.RepositorioPromedioGeneral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ServicioGeneradorPDF {

    private final RepositorioPromedioGeneral promedioGeneralRepository;

    @Autowired
    public ServicioGeneradorPDF(RepositorioPromedioGeneral promedioGeneralRepository) {
        this.promedioGeneralRepository = promedioGeneralRepository;
    }

    public byte[] generatePdf(Long promedioId) {
        Document document = new Document();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PromedioGeneral promedioGeneral = promedioGeneralRepository.findById(promedioId).orElse(null);

        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();

            document.add(new Paragraph("Informe del promedio de evaluaciones con ID: " + promedioGeneral.getId()));
            document.add(new Paragraph("Empresa evaluada: " + promedioGeneral.getEmpresa().getNombre()));
            document.add(new Paragraph("Empresa ID: " + promedioGeneral.getEmpresa().getId()));
            document.add(new Paragraph("Visador a cargo: " + (promedioGeneral.getVisador() != null ? promedioGeneral.getVisador().getNombre() : "")));
            document.add(new Paragraph("Evaluación Compatibilidad: " + promedioGeneral.getNotaCompatibilidad() + " puntos."));
            document.add(new Paragraph("Evaluación Usabilidad: " + promedioGeneral.getNotaUsabilidad() + " puntos."));
            document.add(new Paragraph("Evaluación Fiabilidad: " + promedioGeneral.getNotaFiabilidad() + " puntos."));
            document.add(new Paragraph("Evaluación Seguridad: " + promedioGeneral.getNotaSeguridad() + " puntos."));
            document.add(new Paragraph("Promedio: " + promedioGeneral.getPromedioFinal()));
            document.add(new Paragraph("Observaciones por el Visador: " + promedioGeneral.getObservaciones()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }

        return outputStream.toByteArray();
    }
}