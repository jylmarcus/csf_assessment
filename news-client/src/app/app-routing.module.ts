import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShareNewsComponent } from './components/share-news/share-news.component';
import { SearchNewsComponent } from './components/search-news/search-news.component';

const routes: Routes = [
  {path: '', redirectTo:'/searchNews', pathMatch: 'full'},
  {path: 'searchNews', component: SearchNewsComponent},
  {path: 'shareNews', component: ShareNewsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
