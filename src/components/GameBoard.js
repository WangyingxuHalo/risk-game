import React, { useState } from "react";
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import Spy from '../statics/Spy.png';


const HtmlTooltip = styled(({ className, ...props }) => (
  <Tooltip {...props} classes={{ popper: className }} />
))(({ theme }) => ({
  [`& .${tooltipClasses.tooltip}`]: {
    backgroundColor: '#f5f5f9',
    color: 'rgba(0, 0, 0, 0.87)',
    maxWidth: 220,
    fontSize: theme.typography.pxToRem(12),
    border: '1px solid #dadde9',
  },
}));

const GameBoard = (props) => {
  const [territories, setTerritories] = useState([]);
  const [lines, setLines] = useState([]);
  const [whiteTerrs, setWhiteTerrs] = useState([]);

  React.useEffect(() => {
    setTerritories(props.territories);
    const whiteTerrsTemp = [];
    for (let index = 0; index < props.territories.length; index++) {
      if (props.territories[index].color === 'white') {
        whiteTerrsTemp.push(props.territories[index].territoryName);
      }
    }
    setWhiteTerrs(whiteTerrsTemp);
  }, [props.territories])

  React.useEffect(() => {
    setLines(props.lines);
  }, [props.lines])


  const connectedEndPoint = (endpointName) => {
    for (let idx = 0; idx < whiteTerrs.length; idx++) {
      if (whiteTerrs[idx] === endpointName) {
        return true;
      }
    }
    return false;
  }

  return (
    <svg className="game-board-background" >
      {lines.map((line, lineIdx) => {
        if (!connectedEndPoint(line.start) && !connectedEndPoint(line.end)) {
          return (
            <svg key={lineIdx}>
              <line key={lineIdx} x1={line.x1} y1={line.y1} x2={line.x2} y2={line.y2} stroke="grey" strokeWidth={2} />
              <text x={(line.x1 + line.x2) / 2} y={(line.y1 + line.y2) / 2} style={{ fontSize: 15, stroke:"white", paintOrder: "stroke", strokeWidth: "1.5px"}}>{line.distance}</text>
            </svg>)
        }
        return <div key={lineIdx}></div>

      })}
      {territories.map((territory, territoryIdx) => {
        if (territory.color !== 'white') {
          return (
            <HtmlTooltip key={territoryIdx} arrow
              title={
                <React.Fragment>
                  <Typography color="inherit">{territory.territoryName}</Typography>
                  <p> Food Generation Rate: {territory.foodGene} </p>
                  <p> Tech Generation Rate: {territory.techGene} </p>
                  <p> Number of Spies: {territory.spyNum}</p>
                  {territory.color === 'grey' && <p>Previous Owner: {territory.owner}</p>}
                  <p> Unit:</p>
                  <p> Level&nbsp;&nbsp;&nbsp;Num </p>
                  {territory.units.map((eachUnit, unitIdx) => {
                    return (<div key={unitIdx}>&nbsp;{eachUnit.level}&nbsp;&nbsp;&nbsp;-----&nbsp;&nbsp;&nbsp;{eachUnit.num}</div>)
                  })}
                </React.Fragment>
              }
            >
              <svg>
                <circle cx={territory.cx} cy={territory.cy} r={territory.r} fill={territory.color} stroke="black" strokeWidth={2} />
                <text x={territory.cx} y={territory.cy} strokeWidth="1px" alignmentBaseline="middle" textAnchor="middle" style={{ fontSize: 11 }}>{territory.territoryName}</text>
                {territory.spyNum !== 0 && <image x={territory.cx - 15} y={territory.cy + 5} width="30" height="30" xlinkHref={Spy} />}
              </svg>
            </HtmlTooltip>
          )
        }
        return <div key={territoryIdx}></div>

      })}
    </svg>
  );

};

export default GameBoard;