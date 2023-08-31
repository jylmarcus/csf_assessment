import { News } from './../../models/news';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Location } from '@angular/common';
import { ActivatedRoute, Params, Router } from '@angular/router';
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
  shareNewsId!: String;

  constructor(private fb: FormBuilder, private location: Location, private activatedRoute: ActivatedRoute, private newsSvc: NewsService, private router: Router) {}

  ngOnInit(): void {
    this.shareNewsForm = this.fb.group(
      {
        title: this.fb.control<string>(``, [Validators.required, Validators.minLength(5)]),
        photo: this.fb.control<string>(``,[Validators.required]),
        description: this.fb.control<string>(``, [Validators.required, Validators.minLength(5)]),
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

    this.newsSvc.uploadNews(data).subscribe({
        next: (resp) => {
        this.shareNewsId=resp.newsId;
        alert(this.shareNewsId);
        //alert(`ID of posted news: ${this.shareNewsId}`);
        this.router.navigate(['searchNews'])
      },
        error: (err) => {
          alert(err);
        }
    });
  }

  addTags(addedTags: any) {
    let newTags = addedTags.split(' ');
    this.tags = [...this.tags, ...newTags];
    this.tags = this.tags.filter(tag => tag !== '')
  }

  removeTag(event: any) {
    this.tags= this.tags.filter(tag => tag !== event.target.textContent)
  }

  onFileChange(event: any) {
    this.photo = event.target.files[0];
    this.shareNewsForm.controls['photo'].setValue(this.photo ? this.photo.name : '');
  }

  goBack() {
    let previousInterval: Number = this.activatedRoute.snapshot.params['interval']
    this.router.navigate(['searchNews', {interval: previousInterval ? previousInterval : 5}]);
  }
}
