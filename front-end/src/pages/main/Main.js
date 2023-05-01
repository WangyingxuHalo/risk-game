import React, { useState } from "react";
import { Space, Table, Button, InputNumber, Modal, Spin } from 'antd';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const { Column } = Table;

const Main = (props) => {
    const [userId, setUserId] = useState(1);
    const [userName, setUserName] = useState('Apple');
    const [data, setData] = useState([]);
    const [joinNewGameId, setJoinNewGameId] = useState(1);
    const [newGameNum, setNewGameNum] = useState(2);
    const [displayJoinWait, setDisplayJoinWait] = useState(false);
    const [isCreatedSuccessDisplay, setIsCreatedSuccessDisplay] = useState(false);
    const [gameIdCreated, setGameIdCreated] = useState(1);
    const [colorGameCreated, setColorGameCreated] = useState('');
    const navigate = useNavigate();
    const location = useLocation();

    React.useEffect(() => {
        setUserId(location.state.usrId);
        setUserName(location.state.userName);
        const reqUrl = 'http://vcm-32071.vm.duke.edu:1651/games/' + location.state.usrId;
        axios.get(reqUrl)
            .then((res) => {
                const newGameList = [];
                for (let index = 0; index < res.data.games.length; index++) {
                    const newGameItem = { key: (index + 1).toString(), gameId: res.data.games[index].gameID, playerName: res.data.games[index].color, playerId: res.data.games[index].playerID };
                    newGameList.push(newGameItem);
                }
                setData(newGameList);
            })
            .catch(() => {
                alert('error');
            })
    }, [location.state])

    const handleLogOut = () => {
        navigate("/");
    }

    const handleInvolvedJoin = (record) => {
        // request playerId, then add it below
        const reqUrl = 'http://vcm-32071.vm.duke.edu:1651/resumeGame/' + record.gameId + '/' + record.playerId;
        axios.post(reqUrl)
            .then((res) => {
                navigate("/game", { state: { usrId: userId, userName: userName, playerId: record.playerId, color: record.playerName } });
            })
            .catch((res) => {
                alert('error: ', res);
            })
    }

    const onJoinIdChange = (value) => {
        setJoinNewGameId(Math.floor(value));
    }

    const handleJoinNewGame = (value) => {
        const reqUrl = 'http://vcm-32071.vm.duke.edu:1651/joinGame/' + location.state.usrId + '/' + joinNewGameId;
        axios.post(reqUrl)
            .then((res) => {
                if (res.data.playerID === -1) {
                    alert('Game Not Available. Please contact your friend');
                } else {
                    setDisplayJoinWait(true);
                    let reqReadyUrl = 'http://vcm-32071.vm.duke.edu:1651/isGameReady/' + joinNewGameId;
                    let interval = setInterval(() => {
                        axios.get(reqReadyUrl)
                            .then((res_ready) => {
                                // If all join - then go to placement page
                            if (res_ready.data) {
                                clearInterval(interval);
                                navigate("/placement", { state: { usrId: userId, playerId: res.data.playerID, userName: userName, color: res.data.color, gameId: joinNewGameId } });
                            }
                            // Otherwise stay in this page
                            })
                            .catch(() => {
                                alert('error');
                            })
                    }, 5000);
                }
            })
            .catch(() => {
                alert('error');
            })
    }

    const onNewGameIdChange = (value) => {
        setNewGameNum(Math.floor(value));
    }

    const handleCreateNewGame = (value) => {
        let url = 'http://vcm-32071.vm.duke.edu:1651/createGame/' + userId + '/' + newGameNum;
        axios.post(url)
            .then((res) => {
                // Might not need to use set here!!!!!!!!!!!! define a variable below
                setGameIdCreated(res.data.gameID);
                setColorGameCreated(res.data.color);
                setIsCreatedSuccessDisplay(true);

                // req ready
                let reqReadyUrl = 'http://vcm-32071.vm.duke.edu:1651/isGameReady/' + res.data.gameID;
                let interval = setInterval(() => {
                    axios.get(reqReadyUrl)
                        .then((res_ready) => {

                            // If all join - then go to placement page
                            if (res_ready.data) {
                                clearInterval(interval);
                                navigate("/placement", { state: { usrId: userId, playerId: res.data.playerID, userName: userName, color: res.data.color, gameId: res.data.gameID } });
                            }
                            // Otherwise stay in this page
                        })
                        .catch(() => {
                            alert('error');
                        })
                }, 5000);
            })
            .catch(() => {
                alert('error');
            })
    }

    return (
        <div className="outside-container">
            <div className="outside-header">
                <div className="main-page">
                    <div className="main-left">
                        <div className="welcome-info">
                            <h1>Hi, {userName}</h1>
                            <Button type="primary" onClick={handleLogOut}>Log Out</Button>
                        </div>
                        <div className="game-involve-section">
                            <div className="main-title">
                                <h3>Game You are Involved</h3>
                            </div>
                            <div className="game-info">
                                <Table dataSource={data}>
                                    <Column title="Game ID" dataIndex="gameId" key="gameId" />
                                    <Column title="Player Name" dataIndex="playerName" key="playerName" />
                                    <Column
                                        title="Action"
                                        key="action"
                                        render={(record) => (
                                            <Space size="middle">
                                                <Button onClick={() => handleInvolvedJoin(record)}>Join</Button>
                                            </Space>
                                        )}
                                    />
                                </Table>
                            </div>
                        </div>
                    </div>
                    <div className="main-right">
                        <div className="join-game-section">
                            <div className="main-title">
                                <h3>Join a Game</h3>
                            </div>
                            <div className="prompt-input">
                                <label>Game ID: </label>
                                <InputNumber min={1} value={joinNewGameId} onChange={onJoinIdChange} />
                            </div>
                            <Button type="primary" onClick={handleJoinNewGame}>Join</Button>
                        </div>
                        <div className="new-game-section">
                            <div className="main-title"><h3>Start a New Game</h3></div>
                            <div className="prompt-input">
                                <label>PlayerNum: </label>
                                <InputNumber min={1} max={4} value={newGameNum} onChange={onNewGameIdChange} />
                            </div>
                            <Button type="primary" onClick={handleCreateNewGame}>Create Game</Button>
                        </div>
                        <Modal title="Waiting for Other Players to Join" open={displayJoinWait} footer={null} closable={false}>
                            <div className="tips-display-style">
                                <p>You successfully joined game {joinNewGameId}</p>
                                <p>Please wait for others to join</p>
                                <Spin />
                            </div>
                        </Modal>
                        <Modal title="Waiting for Other Players to Join" open={isCreatedSuccessDisplay} footer={null} closable={false}>
                            <div className="tips-display-style">
                                <p>You are the owner of the game</p>
                                <p>Your game ID is {gameIdCreated}</p>
                                <p>Your color is {colorGameCreated}</p>
                                <p>Please wait for others to join</p>
                                <Spin />
                            </div>
                        </Modal>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Main;