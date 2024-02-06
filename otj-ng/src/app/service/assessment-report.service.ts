import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';
import { AssessmentReport } from '../model/assessment-report';

import { ExcelUploadResponse } from '../model/ExcelUploadResponse.model';

@Injectable({
  providedIn: 'root'
})
export class AssessmentReportService{
  baseUrl = "http://localhost:8080/report/generate";
  constructor(private httpClient: HttpClient)  { }

  getAllreport(): Observable<AssessmentReport[]>{
    console.log("getting all reports");
    return this.httpClient.get<AssessmentReport[]>(this.baseUrl);

  }
public uploadfile(file: File): Observable<ExcelUploadResponse> {
  let formParams = new FormData();
  formParams.append('file', file)
  return this.httpClient.post<ExcelUploadResponse>('http://localhost:8080/report/excel-upload', formParams)
}
// deleteItem(id: number): Observable<any> {
//   const url = {this.baseUrl/+{id}};
//   return this.httpClient.delete(url);
// }

}



