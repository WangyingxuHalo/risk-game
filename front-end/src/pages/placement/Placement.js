import React, { useState } from "react";
import { Button, Input, Modal, Form, Spin } from 'antd';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import GameBoard from "../../components/GameBoard.js";

const Placement = (props) => {
    const [userId, setUserId] = useState(1);
    const [userName, setUserName] = useState('Default');
    const [playerId, setPlayerId] = useState(1);
    const [gameId, setGameId] = useState(1);
    const [playerName, setPlayerName] = useState('BLUE');
    const [totalUnits, setTotalUnits] = useState(120);
    const [territoriesToSet, setTerritoriesToSet] = useState([]);
    const [showAlert, setShowAlert] = useState(false);
    const [currSum, setCurrSum] = useState(0);
    const [displayWaitPlacement, setDisplayWaitPlacement] = useState(false);
    const lines = [
        // Michigan to Michigan State
        {
            "x1": 74,
            "y1": 120,
            "x2": 145,
            "y2": 64,
            "distance": 1,
            "start": "Michigan",
            "end": "Michigan State"
        },
        // Michigan to Northwestern
        {
            "x1": 74,
            "y1": 120,
            "x2": 110,
            "y2": 300,
            "distance": 3,
            "start": "Michigan",
            "end": "Northwestern"
        },
        // Michigan to Indiana
        {
            "x1": 74,
            "y1": 120,
            "x2": 160,
            "y2": 200,
            "distance": 4,
            "start": "Michigan",
            "end": "Indiana"
        },
        // Michigan State to Syracuse
        {
            "x1": 160,
            "y1": 64,
            "x2": 430,
            "y2": 44,
            "distance": 8,
            "start": "Michigan State",
            "end": "Syracuse"
        },
        // Michigan State to Ohio State
        {
            "x1": 160,
            "y1": 64,
            "x2": 260,
            "y2": 170,
            "distance": 3,
            "start": "Michigan State",
            "end": "Ohio State"
        },
        // Syracuse to Uconn
        {
            "x1": 430,
            "y1": 44,
            "x2": 580,
            "y2": 68,
            "distance": 4,
            "start": "Syracuse",
            "end": "UConn"
        },
        // Syracuse to Penn State
        {
            "x1": 430,
            "y1": 44,
            "x2": 380,
            "y2": 150,
            "distance": 4,
            "start": "Syracuse",
            "end": "Penn State"
        },
        // Uconn to Upenn
        {
            "x1": 580,
            "y1": 68,
            "x2": 500,
            "y2": 260,
            "distance": 6,
            "start": "UConn",
            "end": "UPenn"
        },
        // Penn State to Upenn
        {
            "x1": 380,
            "y1": 150,
            "x2": 500,
            "y2": 260,
            "distance": 3,
            "start": "Penn State",
            "end": "UPenn"
        },
        // Penn State to Ohio State
        {
            "x1": 380,
            "y1": 150,
            "x2": 260,
            "y2": 170,
            "distance": 2,
            "start": "Penn State",
            "end": "Ohio State"
        },
        // Ohio State to Indiana
        {
            "x1": 250,
            "y1": 170,
            "x2": 160,
            "y2": 200,
            "distance": 3,
            "start": "Ohio State",
            "end": "Indiana"
        },
        // Indiana to Northwestern
        {
            "x1": 160,
            "y1": 200,
            "x2": 110,
            "y2": 320,
            "distance": 3,
            "start": "Indiana",
            "end": "Northwestern"
        },
        // Northwestern to Illinois
        {
            "x1": 110,
            "y1": 300,
            "x2": 74,
            "y2": 415,
            "distance": 2,
            "start": "Northwestern",
            "end": "Illinois"
        },
        // Illinois to Alabama
        {
            "x1": 74,
            "y1": 400,
            "x2": 54,
            "y2": 550,
            "distance": 7,
            "start": "Illinois",
            "end": "Alabama"
        },
        // Alabama to Auburn
        {
            "x1": 40,
            "y1": 540,
            "x2": 150,
            "y2": 495,
            "distance": 1,
            "start": "Alabama",
            "end": "Auburn"
        },
        // Auburn to Florida
        {
            "x1": 150,
            "y1": 495,
            "x2": 260,
            "y2": 505,
            "distance": 6,
            "start": "Auburn",
            "end": "Florida"
        },
        // Auburn to Georgia
        {
            "x1": 150,
            "y1": 495,
            "x2": 320,
            "y2": 608,
            "distance": 6,
            "start": "Auburn",
            "end": "Georgia"
    
        },
        // Georgia to South Carolina
        {
            "x1": 320,
            "y1": 608,
            "x2": 590,
            "y2": 600,
            "distance": 3,
            "start": "Georgia",
            "end": "South Carolina"
        },
        // Florida to Georgia
        {
            "x1": 260,
            "y1": 505,
            "x2": 320,
            "y2": 608,
            "distance": 3,
            "start": "Florida",
            "end": "Georgia"
        },
        // Georgia to UNC
        {
            "x1": 320,
            "y1": 608,
            "x2": 465,
            "y2": 540,
            "distance": 7,
            "start": "Georgia",
            "end": "UNC"
        },
        // UNC to Duke
        {
            "x1": 465,
            "y1": 560,
            "x2": 550,
            "y2": 490,
            "distance": 1,
            "start": "UNC",
            "end": "Duke"
        },
        // Duke to UVA
        {
            "x1": 550,
            "y1": 490,
            "x2": 440,
            "y2": 440,
            "distance": 2,
            "start": "Duke",
            "end": "UVA"
        },
        // Ohio State to WVU
        {
            "x1": 260,
            "y1": 170,
            "x2": 320,
            "y2": 260,
            "distance": 3,
            "start": "Ohio State",
            "end": "WVU"
        },
        // Indiana to Kentucky
        {
            "x1": 160,
            "y1": 200,
            "x2": 220,
            "y2": 300,
            "distance": 2,
            "start": "Indiana",
            "end": "Kentucky"
        },
        // Kentucky to Illinois
        {
            "x1": 220,
            "y1": 300,
            "x2": 74,
            "y2": 400,
            "distance": 5,
            "start": "Kentucky",
            "end": "Illinois"
        },
        // Kentucky to WVU
        {
            "x1": 210,
            "y1": 300,
            "x2": 320,
            "y2": 260,
            "distance": 3,
            "start": "Kentucky",
            "end": "WVU"
        },
        // WVU to Villanova
        {
            "x1": 320,
            "y1": 260,
            "x2": 445,
            "y2": 350,
            "distance": 5,
            "start": "WVU",
            "end": "Villanova"
        },
        // UPenn to Villanova
        {
            "x1": 500,
            "y1": 260,
            "x2": 445,
            "y2": 370,
            "distance": 1,
            "start": "UPenn",
            "end": "Villanova"
        },
        // Villanova to Maryland
        {
            "x1": 445,
            "y1": 350,
            "x2": 550,
            "y2": 380,
            "distance": 2,
            "start": "Villanova",
            "end": "Maryland"
        },
        // Maryland to UVA
        {
            "x1": 550,
            "y1": 380,
            "x2": 440,
            "y2": 440,
            "distance": 3,
            "start": "Maryland",
            "end": "UVA"
        },
        // UVA to Tennessee
        {
            "x1": 440,
            "y1": 440,
            "x2": 300,
            "y2": 400,
            "distance": 5,
            "start": "UVA",
            "end": "Tennessee"
        },
        // Tennessee to WVU
        {
            "x1": 300,
            "y1": 400,
            "x2": 320,
            "y2": 260,
            "distance": 4,
            "start": "Tennessee",
            "end": "WVU"
        },
        // Tennessee to Auburn
        {
            "x1": 300,
            "y1": 400,
            "x2": 150,
            "y2": 495,
            "distance": 8,
            "start": "Tennessee",
            "end": "Auburn"
        },
        // Maryland to NC State
        {
            "x1": 550,
            "y1": 380,
            "x2": 660,
            "y2": 475,
            "distance": 6,
            "start": "Maryland",
            "end": "NC State"
        },
        // NC State to UNC
        {
            "x1": 660,
            "y1": 510,
            "x2": 465,
            "y2": 570,
            "distance": 2,
            "start": "NC State",
            "end": "UNC"
        },
        // NC State to Duke
        {
            "x1": 660,
            "y1": 480,
            "x2": 550,
            "y2": 490,
            "distance": 1,
            "start": "NC State",
            "end": "Duke"
        },
        // NC State to South Carolina
        {
            "x1": 680,
            "y1": 480,
            "x2": 590,
            "y2": 600,
            "distance": 3,
            "start": "NC State",
            "end": "South Carolina"
        },
        // UNC to South Carolina
        {
            "x1": 430,
            "y1": 550,
            "x2": 590,
            "y2": 600,
            "distance": 4,
            "start": "UNC",
            "end": "South Carolina"
        },
    ];
    const [territories, setTerritories] = useState([{
        "territoryName": "Michigan",
        "cx": 74,
        "cy": 120,
        "r": 40,
        "color": "red",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": "",
    },
    {
        "territoryName": "Michigan State",
        "cx": 160,
        "cy": 64,
        "r": 40,
        "color": "green",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Syracuse",
        "cx": 430,
        "cy": 44,
        "r": 40,
        "color": "green",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": "",
    },
    {
        "territoryName": "UConn",
        "cx": 580,
        "cy": 68,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": "",
    },
    {
        "territoryName": "Penn State",
        "cx": 380,
        "cy": 150,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": "",
    },
    {
        "territoryName": "UPenn",
        "cx": 500,
        "cy": 260,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": "",
    },
    {
        "territoryName": "Ohio State",
        "cx": 260,
        "cy": 170,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": "",

    },
    {
        "territoryName": "Indiana",
        "cx": 160,
        "cy": 200,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Northwestern",
        "cx": 110,
        "cy": 300,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Illinois",
        "cx": 74,
        "cy": 400,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Kentucky",
        "cx": 220,
        "cy": 300,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "WVU",
        "cx": 320,
        "cy": 260,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Villanova",
        "cx": 445,
        "cy": 350,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Maryland",
        "cx": 550,
        "cy": 380,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Alabama",
        "cx": 54,
        "cy": 540,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Auburn",
        "cx": 150,
        "cy": 495,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Tennessee",
        "cx": 300,
        "cy": 400,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Florida",
        "cx": 260,
        "cy": 505,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Georgia",
        "cx": 320,
        "cy": 608,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "UVA",
        "cx": 440,
        "cy": 440,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "Duke",
        "cx": 550,
        "cy": 490,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "NC State",
        "cx": 660,
        "cy": 475,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "UNC",
        "cx": 465,
        "cy": 540,
        "r": 40,
        "color": "yellow",
        "foodGene": 10,
        "techGene": 0,
        "units": [],
        "spyNum": 0,
        "owner": ""
    },
    {
        "territoryName": "South Carolina",
        "cx": 590,
        "cy": 600,
        "r": 40,
        "color": "yellow",
        "foodGene": 0,
        "techGene": 10,
        "units": [
            // { "level": 1, "num": 2 },
            // { "level": 3, "num": 7 },
            // { "level": 5, "num": 1 },
        ],
        "spyNum": 0,
        "owner": ""
    },
    ]);
    const territoriesRef = React.useRef(territories);
    const location = useLocation();
    const navigate = useNavigate();

    // eslint-disable-next-line react-hooks/exhaustive-deps
    React.useEffect(() => {
        setUserId(location.state.usrId);
        setUserName(location.state.userName);
        setPlayerId(location.state.playerId);
        setGameId(location.state.gameId);
        // first uppercase
        const currPlayerColor = location.state.color;
        setPlayerName(currPlayerColor);
        const reqUrl = 'http://vcm-32071.vm.duke.edu:1651/gameMap/' + location.state.playerId;
        axios.get(reqUrl)
            .then((res) => {
                // 1. set color for territories
                const responseTerritories = res.data.territories;
                const newTerritories = [...territoriesRef.current];
                for (let index = 0; index < territoriesRef.current.length; index++) {
                    newTerritories[index].color = responseTerritories[newTerritories[index].territoryName].color.toLowerCase();
                }
                setTerritories(newTerritories);

                // 2. set maxNumUnits
                setTotalUnits(res.data.maxUnits);

                // 3. set territoryToSet list
                const currPlayerColorLowerCase = currPlayerColor.toLowerCase();
                const newMyTerrList = [];
                for (let index = 0; index < newTerritories.length; index++) {
                    if (newTerritories[index].color === currPlayerColorLowerCase) {
                        newMyTerrList.push(newTerritories[index].territoryName);
                    }
                }
                setTerritoriesToSet(newMyTerrList);

            })
            .catch((response) => {
            })
    }, [location.state])

    const handleAlertOK = (value) => {
        setShowAlert(false);
    }

    const onFinish = (values) => {
        let sum = 0;
        const returnArr = [];
        for (let index = 0; index < territoriesToSet.length; index++) {
            let terrName = territoriesToSet[index];
            let numAssign = Number(values[terrName]);
            sum += numAssign;
            let content = { "terrName": terrName, "numUnits": Number(values[terrName]) };
            returnArr.push(content);
        }
        if (sum !== totalUnits) {
            setShowAlert(true);
            setCurrSum(sum);
            return;
        }
        const res = { "playerId": playerId, "placements": returnArr };
        axios.post('http://vcm-32071.vm.duke.edu:1651/placement', res)
            .then((res) => {
                // isPlacementDone
                // navigate("/game", { state: { usrId: userId, userName: userName, playerId: playerId, color: playerName} });
                setDisplayWaitPlacement(true);
                let reqReadyUrl = 'http://vcm-32071.vm.duke.edu:1651/isPlacementDone/' + gameId;
                let interval = setInterval(() => {
                    axios.get(reqReadyUrl)
                        .then((res_ready) => {
                            // If all join - then go to placement page
                            if (res_ready.data) {
                                setDisplayWaitPlacement(false);
                                clearInterval(interval);
                                navigate("/game", { state: { usrId: userId, userName: userName, playerId: playerId, color: playerName} });
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
                <div className="game-container">
                    <div className="game-left">
                        <div className="game-title-section border-purple placement-title">
                            <h1 className="game-title-h1">RISC GAME</h1>
                        </div>
                        <div className="game-board-section">
                            <GameBoard territories={territories} lines={lines} />
                        </div>
                    </div>
                    <div className="game-right">
                        <div className="placement-player-info">
                            <h4>You are Player {playerName}</h4>
                        </div>
                        <div className="placement-tips">
                            <h5>Place You Units</h5>
                        </div>
                        <div className="units-left-info">
                            <h4>Units You have in total: {totalUnits}</h4>
                        </div>
                        {showAlert && <Modal title="Error" open={showAlert} onOk={handleAlertOK} onCancel={handleAlertOK}>
                            <p>Your current assignment sum is {currSum}</p>
                            <p>You should assign exactly {totalUnits} units</p>
                            <p>Try to input again!</p>
                        </Modal>}
                        <div className="placement-enter-section">
                            <Form
                                name="basic"
                                labelCol={{
                                    span: 8,
                                }}
                                wrapperCol={{
                                    span: 40,
                                }}
                                style={{
                                    maxWidth: 600,
                                }}
                                initialValues={{
                                    remember: true,
                                }}
                                onFinish={onFinish}
                                onFinishFailed={onFinish}
                                autoComplete="off"
                            >
                                {territoriesToSet.map((item, index) => {
                                    return (<div key={index}><Form.Item
                                        label={item}
                                        name={item}
                                        rules={[
                                            {
                                                required: true,
                                                pattern: new RegExp(/^(0|[1-9][0-9]*)$/, "g"),
                                                message: 'Please enter non-negative integer',
                                            },
                                        ]}
                                    >
                                        <Input className={item} />
                                    </Form.Item></div>);
                                })}

                                <Form.Item
                                    wrapperCol={{
                                        offset: 4,
                                        span: 16,
                                    }}
                                >
                                    <Button type="primary" htmlType="submit">
                                        Done
                                    </Button>
                                </Form.Item>
                            </Form>
                            <Modal title="Please Wait for Other players to finish placement" open={displayWaitPlacement} footer={null} closable={false}>
                                <div className="tips-display-style">
                                    <p>Successfully submit your placement.</p>
                                    <p>Please wait for other players to finish placement</p>
                                    <Spin />
                                </div>
                            </Modal>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Placement;