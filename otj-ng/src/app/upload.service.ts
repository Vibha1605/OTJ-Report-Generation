import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ExcelUploadResponse } from './model/ExcelUploadResponse.model';
 
@Injectable({
 providedIn: 'root'
})
export class UploadService {
 
 constructor(
   private httpClient: HttpClient,
 ) { }
 
 public uploadfile(file: File): Observable<ExcelUploadResponse> {
   let formParams = new FormData();
   formParams.append('file', file)
   return this.httpClient.post<ExcelUploadResponse>('http://localhost:8080/report/excel-upload', formParams)
 }
}
