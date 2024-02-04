import { ExcelUploadResponse } from './model/ExcelUploadResponse.model';
import { UploadService } from './upload.service';
import { Component } from '@angular/core';
 
@Component({
 selector: 'app-root',
 templateUrl: './app.component.html',
 styleUrls: ['./app.component.css']
})
export class AppComponent {
 title(title: any) {
   throw new Error('Method not implemented.');
 }
 
 file!: File;

 response! : ExcelUploadResponse;
 
 constructor(
   private uploadService: UploadService
 ){
 
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
      console.log(this.response);
       //alert("Uploaded");
     })
   } else {
     alert("Please select a file first")
   }
 }
}
