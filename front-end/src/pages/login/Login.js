import React from "react";
import { Input, Button, Form } from 'antd';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = (props) => {
    const navigate = useNavigate();

    const onFinish = (values) => {
        const content = { "username": values.username, "password": values.password };
        axios.post('http://vcm-32071.vm.duke.edu:1651/login', content)
            .then((res) => {
                navigate("/main", {state: {usrId: res.data.userId, userName: values.username}});
            })
            .catch((res) => {
                alert(res.response.data.error);
            })
    };

    const onFinishFailed = (errorInfo) => {
    };

    const handleRegister = () => {
        navigate('/register');
    };

    const handleGoToIntro = () => {
        navigate('/intro');
    };

    return (
        <div className="outside-container">
            <div className="outside-header">
                <div className="form-area">
                    <header>
                        <h1 className="redirect-intro-label" onClick={handleGoToIntro}>RISC GAME</h1>
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
                            onFinishFailed={onFinishFailed}
                            autoComplete="off"
                        >
                            <Form.Item
                                label=""
                                name="username"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Please enter your username!',
                                    },
                                ]}
                            >
                                <Input placeholder="Username" className="username" />
                            </Form.Item>
                            <Form.Item
                                label=""
                                name="password"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Please enter your password!',
                                    },
                                ]}
                            >
                                <Input.Password placeholder="Password" className="password" />
                            </Form.Item>
                            <Form.Item
                                wrapperCol={{
                                    offset: 4,
                                    span: 16,
                                }}
                            >
                                <Button type="primary" htmlType="submit">
                                    Log In
                                </Button>
                            </Form.Item>
                        </Form>
                        <p>Not a user? <span onClick={handleRegister} className="redirect-text">Register</span></p>
                    </header>
                </div>

            </div>
        </div>
    )
}

export default Login;