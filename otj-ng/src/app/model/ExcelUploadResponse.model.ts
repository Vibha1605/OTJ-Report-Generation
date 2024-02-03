import { AssessmentReport } from "./assessment-report";

export interface ExcelUploadResponse {
    status : string;
    errors : string[];
    assessmentReport: AssessmentReport;
}