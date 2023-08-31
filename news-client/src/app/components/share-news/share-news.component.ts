import { News } from './../../models/news';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { NewsService } from 'src/app/services/news.service';

@Component({
  selector: 'app-share-news',
  templateUrl: './share-news.component.html',
  styleUrls: ['./share-news.component.css']
})
export class ShareNewsComponent implements OnInit{

  shareNewsForm!: FormGroup;
  tags: string[] = [];
  photo!: File;

  constructor(private fb: FormBuilder, private location: Location, private activatedRoute: ActivatedRoute, private newsSvc: NewsService) {}

  ngOnInit(): void {
    this.shareNewsForm = this.fb.group(
      {
        title: this.fb.control<string>(`<newsTitle>`, [Validators.required, Validators.minLength(5)]),
        photo: this.fb.control<string>(``,[Validators.required]),
        description: this.fb.control<string>(`<description>`, [Validators.required, Validators.minLength(5)]),
      }
    )
  }

  onSubmit() {
    const value = this.shareNewsForm.value;
    /* let news: News = {
      title: value['title'],
      postDate: Date.now(),
      photoName: value['photo'],
      description: value['description'],
      tags: this.tags
    } */

    const data = new FormData();
    for(const[k, v] of Object.entries(value)) {
      data.append(k, String(v))
    }

    data.append("tags", this.tags.toString());

    data.append("image", this.photo);

    this.newsSvc.uploadNews(data)
  }

  addTags(addedTags: any) {
    let newTags = addedTags.split(' ');
    this.tags = [...this.tags, ...newTags];
  }

  removeTag(event: any) {
    this.tags= this.tags.filter(tag => tag !== event.target.textContent)
  }

  onFileChange(event: any) {
    this.photo = event.target.files[0];
    this.shareNewsForm.controls['photo'].setValue(this.photo ? this.photo.name : '');
  }
}
