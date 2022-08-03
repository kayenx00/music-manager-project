import logo from './logo.svg';
import './App.css';
import React, { useState, useEffect } from 'react';

const App = () => {
   const [posts, setPosts] = useState([]);
   useEffect(() => {
      fetch('/music/all')
         .then((response) => response.json())
         .then((data) => {
            console.log(data.status);
            setPosts(data);
         })
         .catch((err) => {
            console.log(err.message);
         });
   }, []);

//   <div className="App">
//             <header className="App-header">
//               <div className="App-intro">
//                 <h2>Songs</h2>
//                 {data.object.map(object =>
//                     <div>
//                       {object.name}
//                     </div>
//                 )}
//               </div>
//             </header>
//           </div>

};
export default App;
