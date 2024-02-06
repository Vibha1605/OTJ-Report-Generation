import { Component, OnInit } from '@angular/core';
import { AssessmentReport } from '../model/assessment-report';
import { AssessmentReportService } from '../service/assessment-report.service';
import { UploadService } from '../upload.service';
import { ExcelUploadResponse } from '../model/ExcelUploadResponse.model';

@Component({
  selector: 'app-assessment-report',
  templateUrl: './assessment-report.component.html',
  styleUrls: ['./assessment-report.component.css']
})
export class AssessmentReportComponent implements OnInit {
  file!: File;
  response!: ExcelUploadResponse;
  report!: AssessmentReport[];

  constructor(private assessmentReportService: AssessmentReportService, private uploadService: UploadService) { }

  ngOnInit(): void {
    console.log("ngOnInit");
    this.getAllreport();
  }

  getAllreport() {
    console.log("getAllReport");
    this.assessmentReportService.getAllreport().subscribe(data => {
      console.log(data);
      this.report = data;
    });
  }

  onFilechange(event: any) {
    console.log(event.target.files[0])
    this.file = event.target.files[0];
  }

  upload() {
    if (this.file) {
      this.uploadService.uploadfile(this.file).subscribe(resp => {
        console.log(resp);
        this.response = resp;
        console.log(this.response.errors);
        if (resp.status == "success") {
          this.report.unshift(resp.assessmentReport);
        }
        console.log(this.response);
        //alert("Uploaded");
      })
    } else {
      alert("Please select a file first")
    }
  }

  // deleteItem(reportId: number): void {
  //   const confirmDelete = window.confirm('Are you sure you want to delete this report?');

  //   if (confirmDelete) {
  //     this.assessmentReportService.deleteItem(reportId).subscribe(
  //       () => {
  //         // Successful delete
  //         // Optionally, you can reload the list of reports or update the UI accordingly
  //         console.log('Report deleted successfully');
  //         this.getAllReports(); // Refresh the list after deletion
  //       },
  //       error => {
  //         // Handle error
  //         console.error('Error deleting report:', error);
  //       }
  //     );
  //   }
  // }
}