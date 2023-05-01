import React from "react";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from "./pages/login/Login.js";
import Register from "./pages/register/Register.js";
import Main from "./pages/main/Main.js";
import Game from './pages/game/Game.js'
import Placement from "./pages/placement/Placement.js";
import Intro from "./pages/intro/Intro.js";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Login />}></Route>
          <Route path='/register' element={<Register />}></Route>
          <Route path='/main' element={<Main />}></Route>
          <Route path="/game" element={<Game />}></Route>
          <Route path="/placement" element={<Placement />}></Route>
          <Route path="/intro" element={<Intro />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App;
