import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsService } from 'src/app/services/news.service';

@Component({
  selector: 'app-search-news',
  templateUrl: './search-news.component.html',
  styleUrls: ['./search-news.component.css']
})
export class SearchNewsComponent implements OnInit{

  currentInterval = 5;
  intervals = [5, 15, 30, 45, 60];
  tags!: {tag: String, count: Number}[];

  constructor(private activatedRoute: ActivatedRoute, private newsSvc: NewsService, private router: Router) { }

  ngOnInit(): void {
    //getcurrentintervalfromactivatedroute
    this.currentInterval = this.activatedRoute? this.activatedRoute.snapshot.params['interval'] : 5
    //send interval to service
    this.newsSvc.getTrendingTags(this.currentInterval).subscribe(resp=> {this.tags=resp})
  }

  changeInterval(event: any) {
    this.currentInterval = event.target.value;
    this.newsSvc.getTrendingTags(this.currentInterval).subscribe(resp=> {this.tags=resp})
  }

  goShareNews() {
    this.router.navigate(['shareNews', { interval: this.currentInterval}])
  }
}
