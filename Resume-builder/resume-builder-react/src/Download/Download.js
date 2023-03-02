import React from 'react';
import html2canvas from "html2canvas";
import { jsPDF } from "jspdf";
import "./Download.css"
const GenericPdfDownloader = ({rootElementId , downloadFileName}) => {

    const downloadPdfDocument = () => {
        const input = document.getElementById(rootElementId);
        html2canvas(input)
            .then((canvas) => {
                const imgData = canvas.toDataURL('image/png');
                const pdf = new jsPDF('p', 'pt', 'a4', false);
                pdf.addImage(imgData, 'PNG', 0, 0, 600, 0, undefined, false);
                pdf.save(`${downloadFileName}.pdf`);
            })
    }

    return <button className="downloadButton" onClick={downloadPdfDocument}>Download Pdf</button>

}

export default GenericPdfDownloader;