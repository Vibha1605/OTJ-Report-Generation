import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtjcomponentComponent } from './otjcomponent.component';

describe('OtjcomponentComponent', () => {
  let component: OtjcomponentComponent;
  let fixture: ComponentFixture<OtjcomponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OtjcomponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtjcomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
