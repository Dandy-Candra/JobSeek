package com.android.tubes_pbp.PDF

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.Drawable
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable

import com.android.tubes_pbp.R
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PDFMaker {
    companion object {
        fun createPdf(context: Context, bidangPekerjaan: String, deskripsi: String) {
            val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
            val file = File(pdfPath,"pdf_jobseek.pdf")
            FileOutputStream(file)

            val writer = PdfWriter(file)
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)
            pdfDocument.defaultPageSize = PageSize.A4
            document.setMargins(5f,5f,5f,5f)
            @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(context , R.drawable.jobseeklogowhite)

            val bitmap = (d as BitmapDrawable?)!!.bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            val bitmapData = stream.toByteArray()

            // SET IMAGE ALIGNMENT
            val image = Image(ImageDataFactory.create(bitmapData))
            image.setHorizontalAlignment(HorizontalAlignment.CENTER)

            // set size to the image
            image.scaleToFit(150f,150f)



            val namaPengguna = Paragraph("Berhasil melakukan lamaran").setBold().setFontSize(24f)
                .setTextAlignment(TextAlignment.CENTER)
            val group = Paragraph(
                """
            Detail Lamaran
            """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

            val width = floatArrayOf(100f,100f)
            val table = Table(width)

            table.setHorizontalAlignment(HorizontalAlignment.CENTER)
            table.addCell(Cell().add(Paragraph("Bidang Pekerjaan")))
            table.addCell(Cell().add(Paragraph(bidangPekerjaan)))
            table.addCell(Cell().add(Paragraph("Deskripsi Pekerjaan")))
            table.addCell(Cell().add(Paragraph(deskripsi)))
            table.addCell(Cell().add(Paragraph("Tanggal Lamaran")))
            val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
            table.addCell(Cell().add(Paragraph("Waktu Lamaran")))
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

            //add enter



            val line = Paragraph("_____________________________________________________________")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12f)


            val footer = Paragraph("Copyright Jobseek 2022").setFontSize(12f)
                .setTextAlignment(TextAlignment.CENTER)


            val barcodeQrCode = BarcodeQRCode(
                """
                $bidangPekerjaan
                $deskripsi
                ${LocalDate.now().format(dateTimeFormatter)}
                ${LocalTime.now().format(timeFormatter)}
                """.trimIndent()
            )
            val qrCodeObject = barcodeQrCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
            val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(
                HorizontalAlignment.CENTER)



            for (i in 0..7) {
                document.add(Paragraph("                  "))
            }
            document.add(image)
            for (i in 0..7) {
                document.add(Paragraph("                  "))
            }
            document.add(namaPengguna)
            document.add(group)
            document.add(table)
            document.add(qrCodeImage)
            for (i in 0..10) {
                document.add(Paragraph("                  "))
            }
            document.add(line)
            document.add(footer)


            document.close()
            Toast.makeText(context,"PDF Berhasil Dibuat",Toast.LENGTH_SHORT).show()
        }



        }

    }