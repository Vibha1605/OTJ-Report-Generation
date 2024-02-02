import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';
import { AssessmentReport } from '../model/assessment-report';
@Injectable({
  providedIn: 'root'
})
export class AssessmentReportService {

  


  baseUrl = "http://localhost:8080/report";
  constructor(private httpClient: HttpClient)  { }

  getAllreport(): Observable<AssessmentReport[]>{
    return this.httpClient.get<AssessmentReport[]>(this.baseUrl);
  }
}
