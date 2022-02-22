function trimFixed(a){var x=""+a;var m=0;var e=x.length;for(var i=0;i<x.length;i++){var c=x.substring(i,i+1);if(c>="0"&&c<="9"){if(m==0&&c=="0"){}else{m++;}}else if(c==" "||c=="+"||c=="-"||c=="."){}else if(c=="E"||c=="e"){e=i;break;}else{return a;}}
var b=1.0/3.0;var y=""+b;var q=y.indexOf(".");var n;if(q>=0){n=y.length-(q+1);}else{return a;}
if(m<n){return a;}
var p=x.indexOf(".");if(p==-1){return a;}
var w=" ";for(var i=e-(m-n)-1;i>=p+1;i--){var c=x.substring(i,i+1);if(i==e-(m-n)-1){continue;}
if(i==e-(m-n)-2){if(c=="0"||c=="9"){w=c;continue;}else{return a;}}
if(c!=w){if(w=="0"){var z=(x.substring(0,i+1)+x.substring(e,x.length))-0;return z;}else if(w=="9"){var z=(x.substring(0,i)+(""+((c-0)+1))+x.substring(e,x.length))-0;return z;}else{return a;}}}
if(w=="0"){var z=(x.substring(0,p)+x.substring(e,x.length))-0;return z;}else if(w=="9"){var z=x.substring(0,p)-0;var f;if(a>0){f=1;}else if(a<0){f=-1;}else{return a;}
var r=((""+(z+f))+x.substring(e,x.length))-0;return r;}else{return a;}}