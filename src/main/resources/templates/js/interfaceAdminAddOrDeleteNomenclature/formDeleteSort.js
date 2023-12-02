import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Box, Button, FormControl, InputLabel, MenuItem, Select} from "@material-ui/core"
import axios from "axios";
export function FormDeleteSort(){
    const {register, handleSubmit} = useForm()
    const [arrayOptions, setArrayOptions] = useState(['-'])
    function submit(data){
        axios.post('http://localhost:8100/admin/deleteSort', data)
    }

    useEffect(() => {
        axios.post('http://localhost:8100/admin/getTableSorts')
            .then(res => {
                if (res.data){
                    let arrayNames = [];
                    for(let i = 0; i < res.data.length; i++){
                        arrayNames[i] = res.data[i].name
                    }
                    setArrayOptions(arrayNames)
                }
            })
    }, []);
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Вид продукта</InputLabel>
                    <Select
                        label="Вид продукта"
                        {...register('name')}
                    >
                        {arrayOptions.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Удалить сорт
        </Button>
    </Form>;
}