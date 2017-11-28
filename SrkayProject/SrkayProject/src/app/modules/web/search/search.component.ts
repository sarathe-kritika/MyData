import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Diamond } from '../../../common/models/diamond';
import { ApiService } from '../../../common/services/api.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
  providers: [ApiService]
})
export class SearchComponent implements OnInit {

  private diamonds: Diamond[];
  public searchForm: FormGroup;

  constructor(public builder: FormBuilder, private apiService: ApiService) { }

  ngOnInit() {
    this.diamonds = this.getSearchResult();
    this.searchForm = this.builder.group({
      color: [""],
      shape: [""],
      quality: [""],
      size: [""]
    })
  }

  submitForm(value: any) {
  }

  getSearchResult(): Diamond[] {
    return this.apiService.getDiamondProfiles();
  }

}
