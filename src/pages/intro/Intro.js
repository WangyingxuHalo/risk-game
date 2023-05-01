import React from "react";
import { useNavigate } from 'react-router-dom';
import Xiwen from '../../statics/xiwen-circle.png';
import Shravan from '../../statics/shravan-circle.png';
import Yingxu from '../../statics/yingxu-circle.png';
import Raza from '../../statics/raza-circle.png';

const Intro = (props) => {

    const navigate = useNavigate();

    const handleBack = () => {
        navigate('/');
    };

    return (
        <div className="outside-container">
            <div className="outside-header">
                <div className="wrapper">
                    <h1>Team Member</h1>
                    <div className="our_team">
                        <div className="team_member">
                            <div className="member_img">
                                <img src={Xiwen} alt="team_member"/>
                            </div>
                            <h3>Xiwen Shen</h3>
                            <span>Product Manager</span>
                        </div>
                        <div className="team_member">
                            <div className="member_img">
                                <img src={Yingxu} alt="team_member" />
                            </div>
                            <h3>Yingxu Wang</h3>
                            <span>Front-end</span>
                        </div>
                        <div className="team_member">
                            <div className="member_img">
                                <img src={Raza} alt="team_member" />
                            </div>
                            <h3>Raza Lamb</h3>
                            <span>Back-end</span>
                        </div>
                        <div className="team_member">
                            <div className="member_img">
                                <img src={Shravan} alt="team_member" />
                            </div>
                            <h3>Shravan Mysore Seetharam</h3>
                            <span>Back-end</span>
                        </div>
                    </div>
                    <div className="intro-back">
                    <h1 onClick={handleBack}>Back</h1>
                    </div>
                    
                </div>
            </div>
        </div>

    )
}

export default Intro;