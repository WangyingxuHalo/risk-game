import React, { useState, useMemo } from "react";
import { Button, List, Modal, Select, InputNumber, Tooltip, Spin, notification, message } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import closeButton from '../../statics/close.png';
import GameBoard from "../../components/GameBoard.js";
import Spring from '../../statics/spring.png';
import Summer from '../../statics/summer.png';
import Autumn from '../../statics/autumn.png';
import Winter from '../../statics/winter.png';

const Context = React.createContext({
    name: 'Default',
});

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
    // Tennessee to Aluburn
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

const neighbors = {
    "Michigan": ["Michigan State", "Indiana", "Northwestern"],
    "Michigan State": ["Michigan", "Ohio State", "Syracuse"],
    "Syracuse": ["Michigan State", "Penn State", "UConn"],
    "UConn": ["Syracuse", "UPenn"],
    "Indiana": ["Michigan", "Ohio State", "Northwestern", "Kentucky"],
    "Ohio State": ["Michigan State", "Indiana", "WVU", "Penn State"],
    "Penn State": ["Ohio State", "Syracuse", "UPenn"],
    "Northwestern": ["Michigan", "Indiana", "Illinois"],
    "Kentucky": ["Illinois", "Indiana", "WVU"],
    "WVU": ["Kentucky", "Ohio State", "Tennessee"],
    "UPenn": ["Penn State", "UConn", "Villanova"],
    "Illinois": ["Northwestern", "Kentucky", "Alabama"],
    "Tennessee": ["WVU", "Auburn", "UVA"],
    "Villanova": ["WVU", "UPenn", "Maryland"],
    "Maryland": ["Villanova", "UVA", "NC State"],
    "Alabama": ["Illinois", "Auburn"],
    "Auburn": ["Alabama", "Tennessee", "Florida", "Georgia"],
    "UVA": ["Tennessee", "Maryland"],
    "Florida": ["Auburn", "Georgia"],
    "UNC": ["Georgia", "Duke", "South Carolina"],
    "Duke": ["UVA", "UNC", "NC State"],
    "NC State": ["Maryland", "Duke", "South Carolina"],
    "Georgia": ["Auburn", "Florida", "UNC", "South Carolina"],
    "South Carolina": ["Georgia", "UNC", "NC State"]
};

const enemyTerrList = [
    {
        value: 'Michigan',
    },
    {
        value: 'Michigan State',
    },
    {
        value: 'Syracuse',
    },
    {
        value: 'UConn',
    },
    {
        value: 'Indiana',
    },
    {
        value: 'Ohio State',
    },
    {
        value: 'Penn State',
    },
    {
        value: 'Northwestern',
    },
    {
        value: 'Kentucky',
    },
    {
        value: 'WVU',
    },
    {
        value: 'UPenn',
    },
    {
        value: 'Illinois',
    },
    {
        value: 'Tennessee',
    },
    {
        value: 'Villanova',
    },
    {
        value: 'Maryland',
    },
    {
        value: 'Alabama',
    },
    {
        value: 'Auburn',
    },
    {
        value: 'Florida',
    },
    {
        value: 'UVA',
    },
    {
        value: 'Duke',
    },
    {
        value: 'NC State',
    },
    {
        value: 'Georgia',
    },
    {
        value: 'South Carolina',
    },
    {
        value: 'UNC',
    }
];

const levelOptions = [
    { value: '0' },
    { value: '1' },
    { value: '2' },
    { value: '3' },
    { value: '4' },
    { value: '5' },
];

const levelOptionsDes = [
    { value: '0' },
    { value: '1' },
    { value: '2' },
    { value: '3' },
    { value: '4' },
    { value: '5' },
    { value: '6' },
];

const techResearchTable = [20, 40, 80, 160, 320];

const upgradeCost = [3, 8, 19, 25, 35, 50];

const bonusOptionList = [
    { value: 'Food' },
    { value: 'Tech' },
    { value: 'Unit' }
];

const Game = (props) => {
    const [playerId, setPlayerId] = useState(1);
    const [userId, setUserId] = useState(1);
    const [userName, setUserName] = useState('Default');
    const [playerName, setPlayerName] = useState('BLUE');
    const [gameId, setGameId] = useState(1);
    const [playerNum, setPlayerNum] = useState(4);
    const [gameRound, setGameRound] = useState(2);
    const [techLevel, setTechLevel] = useState(2);
    const [totalFood, setTotalFood] = useState(250);
    const [totalTech, setTotalTech] = useState(300);
    const [totalFoodGene, setTotalFoodGene] = useState(35);
    const [totalTechGene, setTotalTechGene] = useState(59);
    const [lastRoundInfo, setLastRoundInfo] = useState(
        [
            // {
            //     "attackedTerritory": "Duke",
            //     "owner": "Blue",
            //     "winner": "Green",
            //     "attackedIt": [{ "name": "Green", "attackTerr": ["UNC", "NCSU"] }, { "name": "Red", "attackTerr": ["Kentucky", "Kantucky"] }]
            // },
            // {
            //     "attackedTerritory": "Duke",
            //     "owner": "Blue",
            //     "winner": "Green",
            //     "attackedIt": [{ "name": "Green", "attackTerr": ["UNC", "NCSU"] }, { "name": "Red", "attackTerr": ["Kentucky", "Kantucky"] }]
            // },
            // {
            //     "attackedTerritory": "Duke",
            //     "owner": "Blue",
            //     "winner": "Green",
            //     "attackedIt": [{ "name": "Green", "attackTerr": ["UNC", "NCSU"] }, { "name": "Red", "attackTerr": ["Kentucky", "Kantucky"] }]
            // }
        ]
    );
    const [orderThisRound, setOrderThisRound] = useState(
        [
            // { "type": "Move", "src": "Duke", "des": "UNC", "unit": [{ "level": 1, "num": 3 }, { "level": 2, "num": 4 }] },
            // { "type": "Move", "src": "DUUU", "des": "NCSU", "unit": [{ "level": 1, "num": 5 }, { "level": 2, "num": 1 }] },
            // { "type": "Attack", "src": "Duke", "des": "UNC", "unit": [{ "level": 1, "num": 3 }, { "level": 2, "num": 4 }] },
            // { "type": "Attack", "src": "Duke", "des": "NCSU", "unit": [{ "level": 1, "num": 5 }, { "level": 2, "num": 1 }] }
        ]
    );
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
    const [myTerrList, setMyTerrList] = useState(
        [
            // {
            //     value: 'Michigan',
            // },
            // {
            //     value: 'Michigan State',
            // },
            // {
            //     value: 'Syracuse',
            // },
            // {
            //     value: 'Uconn',
            // },
            // {
            //     value: 'Upenn',
            // },
        ]
    );
    const [displayMove, setDisplayMove] = useState(false);
    const [levelUnits, setLevelUnits] = useState([0, 0, 0, 0, 0, 0, 0]);
    const [moveFromChosen, setMoveFromChosen] = useState('');
    const [moveToChosen, setMoveToChosen] = useState('');
    const [moveToSpyChosen, setMoveToSpyChosen] = useState('');
    const [displayAttack, setDisplayAttack] = useState(false);
    const [attackFromChosen, setAttackFromChosen] = useState('');
    const [attackToChosen, setAttackToChosen] = useState('');
    const [displayResearch, setDisplayResearch] = useState(false);
    const [disableResearchClick, setDisableResearchClick] = useState(false);
    const [disableCloakClick, setDisableCloakClick] = useState(false);
    const [displayCantResearch, setDisplayCantRsearch] = useState(false);
    const [displayUpgrade, setDisplayUpgrade] = useState(false);
    const [upgradeTerr, setUpgradeTerr] = useState('');
    const [itemIndexToDelete, setItemIndexToDelete] = useState(1);
    const [isDeleteConfirmOpen, setIsDeleteConfirmOpen] = useState(false);
    const [upgradeList, setUpgradeList] = useState([{ 'srcLevel': 0, 'desLevel': 0, 'num': 0 }]);
    const [displayWaitSubmit, setDisplayWaitSubmit] = useState(false);
    const [displayWinner, setDisplayWinner] = useState(false);
    const [gameWinner, setGameWinner] = useState(undefined);
    const [hasLost, setHasLost] = useState(false);
    const [upgradeSum, setUpgradeSum] = useState(0);
    const [displayUpgradeSpy, setDisplayUpgradeSpy] = useState(false);
    const [displayMoveSpy, setDisplayMoveSpy] = useState(false);
    const [displayCloak, setDisplayCloak] = useState(false);
    const [terrToCloak, setTerrToCloak] = useState('');
    const [weather, setWeather] = useState('Spring');
    const [displayBonus, setDisplayBonus] = useState(false);
    const [bonusOptionSelected, setBonusOptionSelected] = useState('Food');
    const [bonusToTerr, setBonusToTerr] = useState('');
    const [bonusRolls, setBonusRolls] = useState(0);
    const [moveSpyToList, setMoveSpyToList] = useState([]);
    const [api, contextHolder] = notification.useNotification();
    const location = useLocation();
    const navigate = useNavigate();
    const [messageApi, msgContextHolder] = message.useMessage();

    const contextValue = useMemo(
        () => ({
            name: 'Risk Game',
        }),
        [],
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
    const openNotification = (placement, playerColor, round) => {
        api.info({
            message: `Risk Game Notification`,
            description: <Context.Consumer>{() => `Hello player ${playerColor}, it's round ${round} now.`}</Context.Consumer>,
            placement,
            style: {
                marginTop: '5vh',
            },
        });
    };

    const displayTipSuccess = (weatherMessage) => {
        messageApi.open({
            type: 'success',
            content: weatherMessage,
            duration: 999,
            style: {
                marginTop: '-2vh',
            },
        });
    };

    React.useEffect(() => {
        setUserId(location.state.usrId);
        setPlayerId(location.state.playerId);
        setUserName(location.state.userName);
        setPlayerName(location.state.color);

        // request game and map info
        const reqUrl = 'http://vcm-32071.vm.duke.edu:1651/allInfo/' + location.state.playerId;
        axios.get(reqUrl)
            .then((res) => {
                // 1. init game info
                const resTurnInfo = res.data.resTurnInfo;
                if (resTurnInfo.winnerName !== null) {
                    setGameWinner(resTurnInfo.winnerName);
                    setDisplayWinner(true);
                    return;
                }
                setPlayerName(resTurnInfo.playerName);
                setGameId(resTurnInfo.gameID);
                setPlayerNum(resTurnInfo.playNum);
                setGameRound(resTurnInfo.turnNO);
                openNotification('topLeft', resTurnInfo.playerName, resTurnInfo.turnNO);
                setTechLevel(resTurnInfo.techLevel);
                setDisableCloakClick(resTurnInfo.techLevel < 3);
                setTotalFood(resTurnInfo.totalFood);
                setTotalTech(resTurnInfo.totalTech);
                setTotalFoodGene(resTurnInfo.genFood);
                setTotalTechGene(resTurnInfo.genTech);
                setWeather(res.data.season.season);
                displayTipSuccess(res.data.season.description);

                const myTerrs = [];
                for (let index = 0; index < resTurnInfo.territoryNames.length; index++) {
                    myTerrs.push({ value: resTurnInfo.territoryNames[index] });
                }
                setMyTerrList(myTerrs);
                myTerrs.length !== 0 && setMoveSpyToList(getNeighborList(neighbors[myTerrs[0].value]));
                setMoveFromChosen(myTerrs.length === 0 ? '' : myTerrs[0].value);
                setMoveToChosen(myTerrs.length === 0 ? '' : myTerrs[0].value);
                myTerrs.length !== 0 && setMoveToSpyChosen(neighbors[myTerrs[0].value][0]);
                setAttackFromChosen(myTerrs.length === 0 ? '' : myTerrs[0].value);
                setAttackToChosen(myTerrs.length === 0 ? '' : myTerrs[0].value);
                setUpgradeTerr(myTerrs.length === 0 ? '' : myTerrs[0].value);
                setTerrToCloak(myTerrs.length === 0 ? '' : myTerrs[0].value);
                setBonusToTerr(myTerrs.length === 0 ? '' : myTerrs[0].value);

                // 2. init map info
                const responseTerritories = res.data.resGameMap.territories;
                const newTerritories = [...territoriesRef.current];
                for (let index = 0; index < territoriesRef.current.length; index++) {
                    newTerritories[index].color = responseTerritories[newTerritories[index].territoryName].color.toLowerCase();
                    newTerritories[index].units = responseTerritories[newTerritories[index].territoryName].numUnit;
                    newTerritories[index].spyNum = responseTerritories[newTerritories[index].territoryName].spyNum;
                    newTerritories[index].owner = responseTerritories[newTerritories[index].territoryName].owner;
                }
                setTerritories(newTerritories);

                // 3. init last round info
                const resLastRoundList = res.data.resLastRoundInfo.lastRoundInfo;
                const newLastRounds = [];
                for (let attackIdx = 0; attackIdx < resLastRoundList.length; attackIdx++) {
                    const newAttackedIt = { "attackedTerritory": resLastRoundList[attackIdx].attackedTerritory, "owner": resLastRoundList[attackIdx].owner, "winner": resLastRoundList[attackIdx].winner, "attackedIt": resLastRoundList[attackIdx].attackedIt };
                    newLastRounds.push(newAttackedIt);
                }
                setLastRoundInfo(newLastRounds);

                // 4. If lost, continuously request game round diff
                if (resTurnInfo.territoryNames.length === 0) {
                    setHasLost(true);
                    const urlReqTurn = 'http://vcm-32071.vm.duke.edu:1651/getTurn/' + resTurnInfo.gameID;
                    let interval = setInterval(() => {
                        axios.get(urlReqTurn)
                            .then((res_turn) => {
                                if (res_turn.data.turn !== resTurnInfo.turnNO) {
                                    clearInterval(interval);
                                    window.location.reload(true);
                                }
                            })
                            .catch((res) => {
                                alert(res);
                            })
                    }, 5000);
                }

            })
            .catch((res) => {
                alert('error in init');
            })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [location.state])

    const sumBetween = (srcLevel, desLevel) => {
        let sum = 0;
        for (let index = srcLevel; index < desLevel; index++) {
            sum += upgradeCost[index];
        }
        return sum;
    }

    React.useEffect(() => {
        let sum = 0;
        for (let index = 0; index < upgradeList.length; index++) {
            if (upgradeList[index].srcLevel < upgradeList[index].desLevel) {
                sum += sumBetween(upgradeList[index].srcLevel, upgradeList[index].desLevel) * upgradeList[index].num;
            }
        }
        setUpgradeSum(sum);
    }, [upgradeList])

    const handleSwitchGame = () => {
        navigate("/main/", { state: { usrId: userId, userName: userName } });
    }

    const handleLogOut = () => {
        navigate("/");
    }

    const deleteListConfirm = (index) => {
        setItemIndexToDelete(index);
        setIsDeleteConfirmOpen(true);
    }

    const handleDeleteOk = () => {
        // if it's research order, then re-able the research button
        if (orderThisRound[itemIndexToDelete].type === 'Research') {
            setDisableResearchClick(false);
        }
        const newOrders = [...orderThisRound];
        newOrders.splice(itemIndexToDelete, 1);
        setOrderThisRound(newOrders);
        setIsDeleteConfirmOpen(false);
    }

    const handleDeleteCancel = () => {
        setIsDeleteConfirmOpen(false);
    }

    const handleMoveClick = () => {
        setLevelUnits([0, 0, 0, 0, 0, 0, 0]);
        setDisplayMove(true);
    }

    const handleAttackClick = () => {
        setLevelUnits([0, 0, 0, 0, 0, 0, 0]);
        setDisplayAttack(true);
    }

    const handleMoveOK = () => {
        const newOrders = [...orderThisRound];
        const newOrder = { "type": "Move", "src": moveFromChosen, "des": moveToChosen, unit: [] };
        let countChange = 0;
        for (let index = 0; index < 7; index++) {
            if (levelUnits[index] !== 0) {
                countChange++;
                const unitToAdd = { "level": index, "num": levelUnits[index] };
                newOrder.unit.push(unitToAdd);
            }
        }
        // only when changes larger than 0, it will then create that request
        if (countChange > 0) {
            newOrders.push(newOrder);
            setOrderThisRound(newOrders);
        }
        setDisplayMove(false);
    }

    const handleMoveCancel = () => {
        setDisplayMove(false);
    }

    const handleMoveFromChange = (value) => {
        setMoveFromChosen(value);
    };

    const handleMoveFromSpyChange = (value) => {
        setMoveFromChosen(value);
        setMoveSpyToList(getNeighborList(neighbors[value]));
        setMoveToSpyChosen(neighbors[value][0]);
    };

    const handleMoveToSpyChange = (value) => {
        setMoveToSpyChosen(value);
    };

    const handleMoveToChange = (value) => {
        setMoveToChosen(value);
    };

    const onUnitChange = idx => (value) => {
        const submitList = [...levelUnits];
        submitList[idx] = value;
        setLevelUnits(submitList);
    };

    const handleAttackOK = () => {
        const newOrders = [...orderThisRound];
        const newOrder = { "type": "Attack", "src": attackFromChosen, "des": attackToChosen, unit: [] };
        let countChange = 0;
        for (let index = 0; index < 7; index++) {
            if (levelUnits[index] !== 0) {
                countChange++;
                const unitToAdd = { "level": index, "num": levelUnits[index] };
                newOrder.unit.push(unitToAdd);
            }
        }
        // only when changes larger than 0, it will then create that request
        if (countChange > 0) {
            newOrders.push(newOrder);
            setOrderThisRound(newOrders);
        }
        setDisplayAttack(false);
    };

    const handleAttackCancel = () => {
        setDisplayAttack(false);
    }

    const handleAttackFromChange = (value) => {
        setAttackFromChosen(value);
    };

    const handleCloakChange = (value) => {
        setTerrToCloak(value);
    }

    const handleAttackToChange = (value) => {
        setAttackToChosen(value);
    };

    const handleResearchClick = () => {
        if (techLevel < 6) {
            setDisplayResearch(true);
        } else {
            setDisplayCantRsearch(true);
        }

    };

    const handleResearchOK = () => {
        const newOrders = [...orderThisRound];
        const newOrder = { "type": "Research", unit: [] };
        newOrders.push(newOrder);
        setOrderThisRound(newOrders);
        setDisableResearchClick(true);
        setDisplayResearch(false);
    };

    const handleResearchCancel = () => {
        setDisplayResearch(false);
    }

    const handleCantResearchOK = () => {
        setDisableResearchClick(true);
        setDisplayCantRsearch(false);
    }

    const handleCantResearchCancel = () => {
        setDisableResearchClick(true);
        setDisplayCantRsearch(false);
    }

    const handleUpgradeClick = () => {
        setLevelUnits([0, 0, 0, 0, 0, 0, 0]);
        setUpgradeSum(0);
        setDisplayUpgrade(true);
    };

    const handleUpgradeOK = () => {
        const newOrders = [...orderThisRound];
        const newOrder = { "type": "Upgrade", "src": upgradeTerr, unit: [] };
        let countChange = 0;
        for (let index = 0; index < upgradeList.length; index++) {
            if (upgradeList[index].num !== 0) {
                countChange++;
                const unitToAdd = { "srcLevel": upgradeList[index].srcLevel, "desLevel": upgradeList[index].desLevel, "num": upgradeList[index].num };
                newOrder.unit.push(unitToAdd);
            }
        }
        // only when changes larger than 0, it will then create that request
        if (countChange > 0) {
            newOrders.push(newOrder);
            setOrderThisRound(newOrders);
        }
        setUpgradeList([{ 'srcLevel': 1, 'desLevel': 1, 'num': 0 }]);
        setDisplayUpgrade(false);
    }

    const handleUpgradeCancel = () => {
        setUpgradeList([{ 'srcLevel': 1, 'desLevel': 1, 'num': 0 }]);
        setDisplayUpgrade(false);
    }

    const handleUpgradeTerrChange = (value) => {
        setUpgradeTerr(value);
    };

    const handleSrcLevelChange = idx => (value) => {
        const newUpgradeList = [...upgradeList];
        const newSrcLevel = parseInt(value);
        newUpgradeList[idx].srcLevel = newSrcLevel;
        setUpgradeList(newUpgradeList);
        // setUpgradeSrcLevel(parseInt(value));
    };

    const handleDesLevelChange = idx => (value) => {
        const newUpgradeList = [...upgradeList];
        const newDesLevel = parseInt(value);
        newUpgradeList[idx].desLevel = newDesLevel;
        setUpgradeList(newUpgradeList);
    };

    const onUpgradeNumChange = idx => (value) => {
        // setUpgradeNum(value);
        // If not chosen it will be null. In this case just set it to 0.
        if (value === null) {
            value = 0;
        }
        const newUpgradeList = [...upgradeList];
        newUpgradeList[idx].num = value;
        setUpgradeList(newUpgradeList);
    };

    const handleAddSingleUpgrade = () => {
        const newUpgradeList = [...upgradeList, { 'srcLevel': 0, 'desLevel': 0, 'num': 0 }];
        setUpgradeList(newUpgradeList);
    };

    const handleSubmitOrders = () => {
        // If the number of orders is 0, sacrifice this turn
        if (orderThisRound.length === 0) {
            setDisplayBonus(true);
            return;
        }
        // If there are orders in this round
        const submitContent =
        {
            "playerID": playerId,
            "turnNum": gameRound,
            "move": [],
            "attack": [],
            "research": false,
            "techLevel": techLevel,
            "upgrade": [],
            "spyUpgrade": [],
            "spyMove": [],
            "clocking": []
        };
        console.log(submitContent);
        for (let index = 0; index < orderThisRound.length; index++) {
            switch (orderThisRound[index].type) {
                case "Move":
                    const moveToAdd = { "src": orderThisRound[index].src, "des": orderThisRound[index].des, "units": [...orderThisRound[index].unit] };
                    submitContent.move.push(moveToAdd);
                    break;
                case "Attack":
                    const attackToAdd = { "src": orderThisRound[index].src, "des": orderThisRound[index].des, "units": [...orderThisRound[index].unit] };
                    submitContent.attack.push(attackToAdd);
                    break;
                case "Research":
                    submitContent.research = true;
                    break;
                case "Upgrade":
                    for (let upgradeIdx = 0; upgradeIdx < orderThisRound[index].unit.length; upgradeIdx++) {
                        const upgradeToAdd = { "num": orderThisRound[index].unit[upgradeIdx].num, "before": orderThisRound[index].unit[upgradeIdx].srcLevel, "after": orderThisRound[index].unit[upgradeIdx].desLevel, "terrName": orderThisRound[index].src };
                        submitContent.upgrade.push(upgradeToAdd);
                    }
                    break;
                case "UpgradeSpy":
                    const upgradeSpyToAdd = { "num": 1, "terrName": orderThisRound[index].src };
                    submitContent.spyUpgrade.push(upgradeSpyToAdd);
                    break;
                case "MoveSpy":
                    const moveSpyToAdd = { "src": orderThisRound[index].src, "des": orderThisRound[index].des, "num": 1 };
                    submitContent.spyMove.push(moveSpyToAdd);
                    break;
                case "Cloak":
                    submitContent.clocking.push(orderThisRound[index].src);
                    break;
                default:
            }
        }
        axios.post('http://vcm-32071.vm.duke.edu:1651/done', submitContent)
            .then((res) => {
                if (res.data.result !== null) {
                    alert(res.data.result);
                    return;
                }

                setDisplayWaitSubmit(true);

                const urlReqTurn = 'http://vcm-32071.vm.duke.edu:1651/getTurn/' + gameId;

                let interval = setInterval(() => {
                    axios.get(urlReqTurn)
                        .then((res_turn) => {
                            if (res_turn.data.turn !== gameRound) {
                                setDisplayWaitSubmit(false);
                                clearInterval(interval);
                                window.location.reload(true);
                            }
                        })
                        .catch((res) => {
                            alert(res);
                        })
                }, 5000);

            })
            .catch((res) => {
                alert(res);
            })

    };

    const handleUpgradeSpy = () => {
        setDisplayUpgradeSpy(true);
    };

    const handleUpgradeSpyOK = () => {
        const newOrderThisRound = [...orderThisRound];
        const newOrder = { "type": "UpgradeSpy", "src": upgradeTerr, "num": 1 };
        newOrderThisRound.push(newOrder);
        setOrderThisRound(newOrderThisRound);
        setDisplayUpgradeSpy(false);
    }

    const handleUpgradeSpyCancel = () => {
        setDisplayUpgradeSpy(false);
    }

    const handleMoveSpy = () => {
        setDisplayMoveSpy(true);
    }

    const handleMoveSpyOK = () => {
        const newOrderThisRound = [...orderThisRound];
        const newOrder = { "type": "MoveSpy", "src": moveFromChosen, "des": moveToSpyChosen, "num": 1 };
        newOrderThisRound.push(newOrder);
        setOrderThisRound(newOrderThisRound);
        setDisplayMoveSpy(false);
    }

    const handleMoveSpyCancel = () => {
        setDisplayMoveSpy(false);
    }

    const handleCloak = () => {
        setDisplayCloak(true);
    }

    const handleCloakOK = () => {
        const newOrderThisRound = [...orderThisRound];
        const newOrder = { "type": "Cloak", "src": terrToCloak };
        newOrderThisRound.push(newOrder);
        setOrderThisRound(newOrderThisRound);
        setDisplayCloak(false);
    }

    const handleCloakCancel = () => {
        setDisplayCloak(false);
    }

    const handleBonusOK = () => {
        const submitContent =
        {
            "type": bonusOptionSelected,
            "terr": bonusOptionSelected === 'Unit' ? bonusToTerr : null,
            "playerId": playerId
        };
        console.log(submitContent);
        axios.post('http://vcm-32071.vm.duke.edu:1651/bonus', submitContent)
            .then((res) => {
                if (res.data.result !== null) {
                    setBonusRolls(res.data.result);
                }
                setDisplayWaitSubmit(true);

                const urlReqTurn = 'http://vcm-32071.vm.duke.edu:1651/getTurn/' + gameId;

                let interval = setInterval(() => {
                    axios.get(urlReqTurn)
                        .then((res_turn) => {
                            if (res_turn.data.turn !== gameRound) {
                                setDisplayWaitSubmit(false);
                                clearInterval(interval);
                                window.location.reload(true);
                            }
                        })
                        .catch((res) => {
                            alert(res);
                        })
                }, 5000);

            })
            .catch((res) => {
                alert(res);
            })
        setDisplayBonus(false);
    }

    const handleBonusCancel = () => {
        setDisplayBonus(false);
    }

    const handleBonusOptionChange = (value) => {
        setBonusOptionSelected(value);
    }

    const handleBonusUnitChange = (value) => {
        setBonusToTerr(value);
    }

    const getNeighborList = (neighborList) => {
        const resList = [];
        for (let index = 0; index < neighborList.length; index++) {
            resList.push({value: neighborList[index]});
        }
        return resList;
    }

    return (
        <Context.Provider value={contextValue}>
            {msgContextHolder}
            {contextHolder}
            <div className="outside-container" style={{ backgroundImage: `url(${weather === 'Spring' ? Spring : weather === 'Summer' ? Summer : weather === 'Fall' ? Autumn : Winter})`}}>
                <div className="outside-header">
                    <div className="whole-content">
                        {gameWinner === undefined && <div className="game-container">
                            <div className="game-left">
                                <div className="game-title-section">
                                    <div className="game-title-left">
                                        <div className="border-purple">
                                            <h1 className="game-title-h1">RISC GAME</h1>
                                        </div>
                                        <div className="border-purple">
                                            <h1 className="game-title-h1">Player: {playerName}</h1>
                                        </div>
                                    </div>
                                    <div className="game-title-right border-purple">
                                        <div className="game-id-section">
                                            <h5>Game ID: {gameId}</h5>
                                        </div>
                                        <div className="player-display-section">
                                            <div className="player-display-left">
                                                <h5>Players</h5>
                                            </div>
                                            <div className="player-display-right">
                                                <h5 className="each-player-info">Red</h5>
                                                <h5 className="each-player-info">Blue</h5>
                                                {playerNum > 2 && <h5 className="each-player-info">Green</h5>}
                                                {playerNum > 3 && <h5 className="each-player-info">Yellow</h5>}
                                            </div>
                                        </div>
                                        <div className="round-info">
                                            <h5>Round: {gameRound}</h5>
                                        </div>
                                    </div>
                                    <div className="control-button-section">
                                        <Button className="control-button" type="primary" onClick={handleSwitchGame} >SWITCH GAME</Button>
                                        <Button className="control-button" type="primary" onClick={handleLogOut}>LOG OUT</Button>
                                    </div>
                                </div>
                                <div className="game-board-section">
                                    <GameBoard territories={territories} lines={lines}/>
                                </div>
                            </div>
                            <div className="game-right">
                                <div className="resource-tech-info border-purple">

                                    <div className="tech-info-detail">
                                        <Tooltip title="Tech Level decides the maxiumum level of your army">
                                            <h5 className="resource-content">Tech Level</h5>
                                        </Tooltip>
                                        <h6 className="resource-content">{techLevel}</h6>
                                    </div>

                                    <div className="resource-info">
                                        <div className="resource-info-detail">
                                            <h5 className="resource-content">Total Resource</h5>
                                            <Tooltip title="It costs food resrouce to send army when moving or attacking">
                                                <h6 className="resource-content">Food: {totalFood}</h6>
                                            </Tooltip>
                                            <Tooltip title="It costs technology resrouce to upgrade your army or tech level">
                                                <h6 className="resource-content">Technology: {totalTech}</h6>
                                            </Tooltip>

                                        </div>
                                        <div className="resource-info-detail">
                                            <Tooltip title="Total generation of food or technology per round with the territories you have for now">
                                                <h5 className="resource-content">Generation Rate</h5>
                                            </Tooltip>
                                            <h6 className="resource-content">Food: {totalFoodGene} / Round</h6>
                                            <h6 className="resource-content">Technology: {totalTechGene} / Round</h6>
                                        </div>
                                    </div>
                                </div>

                                <div className="last-round-info border-purple">
                                    <h5>What Happened in Last Round</h5>
                                    <div className="last-round-list">
                                        <List
                                            style={{ width: '480px', marginTop: '10px', textAlign: 'left' }}
                                            size="small"
                                            dataSource={lastRoundInfo}
                                            renderItem={(item, index) => <List.Item
                                            ><div className="last-round-list-item">
                                                    <div className="last-round-list-title">
                                                        <h5>{index + 1}. Territory {item.attackedTerritory}, originally owned by {item.owner} was attacked</h5>
                                                        {item.attackedIt.map((attacker, attackerIdx) => {
                                                            return (<div className="last-round-each-attacker" key={attackerIdx}>Attacker {attacker.name} attacked from {attacker.attackTerr.map((attackTerr, terrIdx) => {
                                                                return (attackTerr + ' ')
                                                            })}</div>)
                                                        })}
                                                        <h4>The winner is {item.winner}</h4>
                                                    </div>
                                                    <div className="last-round-list-detail"></div>
                                                </div></List.Item>}
                                        />
                                    </div>
                                </div>
                                <Modal title="Are you sure you want to delete this order?" open={isDeleteConfirmOpen} onOk={handleDeleteOk} onCancel={handleDeleteCancel}></Modal>
                                {!hasLost && <div className="this-round-order border-purple">
                                    <h5>Your Order in This Round</h5>
                                    <div className="this-round-list">
                                        <List
                                            style={{ width: '480px', marginTop: '10px', textAlign: 'left' }}
                                            size="small"
                                            dataSource={orderThisRound}
                                            renderItem={(item, index) => <List.Item
                                                actions={[<span key="list-loadmore-edit" onClick={() => deleteListConfirm(index)}><img
                                                    src={closeButton}
                                                    alt="close"
                                                    width="17px"
                                                    height="17px" /></span>]}
                                            ><div className="last-round-list-item">
                                                    <div className="last-round-list-title">
                                                        {(item.type === 'Move' || item.type === 'Attack') && (
                                                            <div>
                                                                <h5>{index + 1}. {item.type} from {item.src} to {item.des} with</h5>
                                                                {item.unit.map((eachUnit, unitIdx) => {
                                                                    return (
                                                                        <div className="last-round-each-attacker" key={unitIdx}>{eachUnit.num} units in level {eachUnit.level}</div>)
                                                                })}
                                                            </div>)}
                                                        {item.type === 'Research' && <h5>{index + 1}. Do research: tech level upgrade from level {techLevel} to {techLevel + 1}</h5>}
                                                        {item.type === 'Upgrade' &&
                                                            <div>
                                                                <h5>{index + 1}. {item.type} in {item.src}</h5>
                                                                {item.unit.map((eachUnit, unitIdx) => {
                                                                    return (
                                                                        <div className="last-round-each-attacker" key={unitIdx}>{eachUnit.num} units from level {eachUnit.srcLevel} to level {eachUnit.desLevel}</div>
                                                                    )
                                                                })}
                                                            </div>}
                                                        {item.type === 'UpgradeSpy' &&
                                                            <div>
                                                                <h5>{index + 1}. Upgrade 1 unit in {item.src} to spy</h5>
                                                            </div>
                                                        }
                                                        {item.type === 'MoveSpy' &&
                                                            <div>
                                                                <h5>{index + 1}. Move 1 spy from {item.src} to {item.des}</h5>
                                                            </div>
                                                        }
                                                        {item.type === 'Cloak' &&
                                                            <div>
                                                                <h5>{index + 1}. Cloak {item.src}</h5>
                                                            </div>
                                                        }

                                                    </div>
                                                </div></List.Item>}
                                        />
                                    </div>
                                </div>}
                                {!hasLost && <div className="order-button-section">
                                    <Button className="order-button" type="primary" onClick={handleMoveClick}>Move</Button>
                                    <Button className="order-button" type="primary" onClick={handleAttackClick}>Attack</Button>
                                    <Button className="order-button" type="primary" onClick={handleResearchClick} disabled={disableResearchClick}>Research</Button>
                                    <Button className="order-button" type="primary" onClick={handleUpgradeClick}>Upgrade</Button>
                                    <Button className="order-button" type="primary" onClick={handleSubmitOrders}>Done</Button>
                                </div>}
                                {!hasLost && <div className="order-button-section">
                                    <Button className="order-button" type="primary" onClick={handleUpgradeSpy}>Upgrade Spy</Button>
                                    <Button className="order-button" type="primary" onClick={handleMoveSpy}>Move Spy</Button>
                                    <Button className="order-button" type="primary" onClick={handleCloak} disabled={disableCloakClick}>Cloak Territory</Button>
                                </div>}
                                {hasLost &&
                                    <div className="lost-tip-display">
                                        <p>You have lost. Please wait for other players to finish their orders</p>
                                        <Spin />
                                    </div>}

                                <Modal title="Move Order" open={displayMove} onOk={handleMoveOK} onCancel={handleMoveCancel}>
                                    <div className="order-outside-container">
                                        <p>A move costs food resources, the formula for the cost is Number of Units X Distance</p>
                                        <div className="order-container">
                                            <div className="option-section">
                                                <p>From</p>
                                                <Select
                                                    value={moveFromChosen}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleMoveFromChange}
                                                    options={myTerrList}
                                                />
                                            </div>
                                            <div className="option-section">
                                                <p>To</p>
                                                <Select
                                                    value={moveToChosen}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleMoveToChange}
                                                    options={myTerrList}
                                                />
                                            </div>
                                            <div className="option-section">
                                                <p>Units</p>
                                                <div className="level-section">
                                                    <p className="level-label">Level 0</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[0]} onChange={onUnitChange(0)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 1</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[1]} onChange={onUnitChange(1)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 2</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[2]} onChange={onUnitChange(2)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 3</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[3]} onChange={onUnitChange(3)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 4</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[4]} onChange={onUnitChange(4)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 5</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[5]} onChange={onUnitChange(5)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 6</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[6]} onChange={onUnitChange(6)} />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </Modal>
                                <Modal title="Attack Order" open={displayAttack} onOk={handleAttackOK} onCancel={handleAttackCancel}>
                                    <div className="order-outside-container">
                                        <p>An attack costs food resources, the formula for the cost is Number of Units X Distance X 2</p>
                                        <div className="order-container">
                                            <div className="option-section">
                                                <p>From</p>
                                                <Select
                                                    value={attackFromChosen}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleAttackFromChange}
                                                    options={myTerrList}
                                                />
                                            </div>
                                            <div className="option-section">
                                                <p>To</p>
                                                <Select
                                                    defaultValue={enemyTerrList[0].value}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleAttackToChange}
                                                    options={enemyTerrList}
                                                />
                                            </div>
                                            <div className="option-section">
                                                <p>Units</p>
                                                <div className="level-section">
                                                    <p className="level-label">Level 0</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[0]} onChange={onUnitChange(0)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 1</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[1]} onChange={onUnitChange(1)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 2</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[2]} onChange={onUnitChange(2)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 3</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[3]} onChange={onUnitChange(3)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 4</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[4]} onChange={onUnitChange(4)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 5</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[5]} onChange={onUnitChange(5)} />
                                                </div>
                                                <div className="level-section">
                                                    <p className="level-label">Level 6</p>
                                                    <InputNumber min={0} defaultValue={0} value={levelUnits[6]} onChange={onUnitChange(6)} />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </Modal>
                                <Modal title="Research Confirmation" open={displayResearch} onOk={handleResearchOK} onCancel={handleResearchCancel}>
                                    <p>Are you sure you want to upgrade your tech level from {techLevel} to {techLevel + 1}?</p>
                                    <p>It will cost {techResearchTable[techLevel - 1]} tech resource</p>
                                </Modal>
                                <Modal title="Error" open={displayCantResearch} onOk={handleCantResearchOK} onCancel={handleCantResearchCancel}>
                                    <p>You can't do research because your tech level is 6 already</p>
                                </Modal>
                                <Modal title="Upgrade Order" open={displayUpgrade} onOk={handleUpgradeOK} onCancel={handleUpgradeCancel}>
                                    <div className="order-container">
                                        <div className="option-section">
                                            <p>Territory to Upgrade</p>
                                            <Select
                                                value={upgradeTerr}
                                                style={{
                                                    width: 150,
                                                }}
                                                onChange={handleUpgradeTerrChange}
                                                options={myTerrList}
                                            />
                                        </div>
                                        <div className="option-section">
                                            <div className="option-titles">
                                                <p>src level</p>
                                                <p>dest level</p>
                                                <p>num</p>
                                            </div>
                                            {upgradeList.map((item, idx) => {
                                                return (<div className="level-section margin-bottom" key={idx}>
                                                    <Select
                                                        defaultValue={levelOptions[0].value}
                                                        value={item.srcLevel}
                                                        style={{
                                                            width: 90,
                                                        }}
                                                        onChange={handleSrcLevelChange(idx)}
                                                        options={levelOptions}
                                                    />
                                                    <Select
                                                        defaultValue={levelOptionsDes[0].value}
                                                        value={item.desLevel}
                                                        style={{
                                                            width: 90,
                                                        }}
                                                        onChange={handleDesLevelChange(idx)}
                                                        options={levelOptionsDes}
                                                    />
                                                    <InputNumber min={0} defaultValue={0} value={item.num} onChange={onUpgradeNumChange(idx)} />
                                                </div>)
                                            })}
                                            <div className="leve-section">
                                                <p>Sum of Cost: {upgradeSum}</p>
                                            </div>
                                            <div className="leve-section">
                                                <Button className="control-button" type="primary" onClick={handleAddSingleUpgrade} >+</Button>
                                            </div>
                                        </div>
                                    </div>
                                </Modal>
                                <Modal title="Upgrade Spy" open={displayUpgradeSpy} onOk={handleUpgradeSpyOK} onCancel={handleUpgradeSpyCancel}>
                                    <div className="order-outside-container">
                                        <p>Spies are a special type of unit you can upgrade. Spies allow you to see what is in an enemy territory, regardless of adjacency. Spies can be upgraded from level 1 units and cost 15 technology units.</p>
                                        <div className="order-container">
                                            <div className="option-section">
                                                <p>Territory to Upgrade</p>
                                                <Select
                                                    value={upgradeTerr}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleUpgradeTerrChange}
                                                    options={myTerrList}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </Modal>
                                <Modal title="Move Spy" open={displayMoveSpy} onOk={handleMoveSpyOK} onCancel={handleMoveSpyCancel}>
                                    <div className="order-outside-container">
                                        <p>Spies can move one territory at a time (only to adjacent territories). Spy moves cost the same as moving other units.</p>
                                        <div className="order-container">
                                            <div className="option-section">
                                                <p>From</p>
                                                <Select
                                                    value={moveFromChosen}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleMoveFromSpyChange}
                                                    options={enemyTerrList}
                                                />
                                            </div>
                                            <div className="option-section">
                                                <p>To</p>
                                                <Select
                                                    value={moveToSpyChosen}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleMoveToSpyChange}
                                                    options={moveSpyToList}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </Modal>
                                <Modal title="Cloak Territory" open={displayCloak} onOk={handleCloakOK} onCancel={handleCloakCancel}>
                                    <div className="order-outside-container">
                                        <p>Cloaking is available at Research level 4. Cloaking allows you to hide a territory from adjacency viewing for 3 turns, and costs 25 technology units.</p>
                                        <div className="order-container">
                                            <div className="option-section">
                                                <p>Territory to cloak</p>
                                                <Select
                                                    value={terrToCloak}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleCloakChange}
                                                    options={myTerrList}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </Modal>
                                {/* ---- */}
                                <Modal title="Sacrifice Turn to Get Bonus" open={displayBonus} onOk={handleBonusOK} onCancel={handleBonusCancel}>
                                    <div className="order-outside-container">
                                        <p>Sacrifice your turn to get a bonus — you cannot do any other moves on your turn except this one, which allows you to roll a twenty-sided dice and receive either that roll times 10 food or technology resources, or that roll number of basic units - these units will be placed in one territory of your choosing.</p>
                                        <div className="order-container">
                                            <div className="option-section">
                                                <p>Bonus Option</p>
                                                <Select
                                                    value={bonusOptionSelected}
                                                    style={{
                                                        width: 150,
                                                    }}
                                                    onChange={handleBonusOptionChange}
                                                    options={bonusOptionList}
                                                />
                                            </div>
                                            {bonusOptionSelected === 'Unit' &&
                                                <div className="option-section">
                                                    <p>Territory to Assign Bonus</p>
                                                    <Select
                                                        value={bonusToTerr}
                                                        style={{
                                                            width: 150,
                                                        }}
                                                        onChange={handleBonusUnitChange}
                                                        options={myTerrList}
                                                    />
                                                </div>
                                            }
                                        </div>
                                    </div>
                                </Modal>
                                {/* ---- */}
                                <Modal title="Please Wait for Next Round" open={displayWaitSubmit} footer={null} closable={false}>
                                    <div className="tips-display-style">
                                        {bonusRolls !== 0 && <p>You get {bonusRolls} for the dice</p>}
                                        <p>Successfully submit your orders.</p>
                                        <p>Please wait for other players to submit</p>
                                        <Spin />
                                    </div>
                                </Modal>

                            </div>
                        </div>}
                        {gameWinner !== undefined && <Modal title="Game Over" open={displayWinner} footer={null} closable={false}>
                            <div className="tips-display-style">
                                <p>Game is over</p>
                                <p>Winner is: {gameWinner}</p>
                                <Button className="control-button" type="primary" onClick={handleSwitchGame} >Go Back</Button>
                            </div>
                        </Modal>}
                    </div>



                </div>
            </div>
        </Context.Provider>

    )
}

export default Game;