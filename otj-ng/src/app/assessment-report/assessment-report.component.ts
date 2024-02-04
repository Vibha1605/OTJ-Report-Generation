import { Component, OnInit } from '@angular/core';
import { AssessmentReport } from '../model/assessment-report';
import { AssessmentReportService } from '../service/assessment-report.service';

@Component({
  selector: 'app-assessment-report',
  templateUrl: './assessment-report.component.html',
  styleUrls: ['./assessment-report.component.css']
})
export class AssessmentReportComponent implements OnInit {

    report!: AssessmentReport[];
      constructor(private assessmentReportService: AssessmentReportService) { }
      
      ngOnInit(): void {
        
      this.getAllreport();
      }
    
      getAllreport(){
        this.assessmentReportService.getAllreport().subscribe(data=>
          {
            console.log(data);
            this.report = data;
          });
        }
    }