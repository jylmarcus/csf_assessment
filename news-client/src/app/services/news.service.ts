import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  uploadNews(data: FormData) {

    return firstValueFrom(this.http.post<any>(`http://localhost:8080/postNews`, data));
  }
}
