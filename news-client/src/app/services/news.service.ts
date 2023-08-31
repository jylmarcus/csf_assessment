import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  uploadNews(data: FormData) {
    return this.http.post<any>(`http://localhost:8080/postNews`, data);
  }

  getTrendingTags(interval: number) {
    const params = new HttpParams().set('interval', interval);
    return this.http.get<any>(`http://localhost:8080/getTags`, {params});
  }
}
