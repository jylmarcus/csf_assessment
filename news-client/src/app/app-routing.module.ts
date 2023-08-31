import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShareNewsComponent } from './components/share-news/share-news.component';

const routes: Routes = [
  {path: '', redirectTo:'/shareNews', pathMatch: 'full'},
  {path: 'shareNews', component: ShareNewsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
