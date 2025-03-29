import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';
import { AssessmentReport } from '../model/assessment-report';
import { catchError } from 'rxjs/operators';

import { ExcelUploadResponse } from '../model/ExcelUploadResponse.model';

@Injectable({
  providedIn: 'root'
})
export class AssessmentReportService{
  baseUrl = "http://localhost:8080/report";
  constructor(private httpClient: HttpClient)  { }

  getAllreport(): Observable<AssessmentReport[]>{
    console.log("getting all reports");
    return this.httpClient.get<AssessmentReport[]>(this.baseUrl+'/generate');

  }

deleteItem(reportId: number): Observable<any> {
  const url = `${this.baseUrl}/delete/${reportId}`;
  return this.httpClient.delete(url).pipe(
    catchError(error => {
      // Handle error here
      console.error('Error deleting report:', error);
      throw error; // Re-throw the error to propagate it to the caller
    })
  );
}


public uploadfile(file: File): Observable<ExcelUploadResponse> {
  let formParams = new FormData();
  formParams.append('file', file)
  return this.httpClient.post<ExcelUploadResponse>('http://localhost:8080/report/excel-upload', formParams)
}



}


