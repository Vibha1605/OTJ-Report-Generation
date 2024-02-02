import { TestBed } from '@angular/core/testing';

import { AssessmentReportService } from './assessment-report.service';

describe('AssessmentReportService', () => {
  let service: AssessmentReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssessmentReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
