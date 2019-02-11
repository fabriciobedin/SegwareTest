import React from 'react'
import {
  Card,
  CardBody,
  CardText,
  Label
} from 'reactstrap';
import { FaChevronUp } from 'react-icons/fa';
import { FaChevronDown } from 'react-icons/fa';

const PostItem = ({ post, upVote, downVote }) => {
  return (
    <Card>
      <CardBody>
        <CardText>{post.text}</CardText>
        <div style={{marginLeft: '38%'}}>
      
          <FaChevronDown className="btn-down-vote" onClick={() => downVote(post)} />{' '} 
          <Label className="prymary" >{post.votes} </Label>{' '}
          <FaChevronUp className="btn-up-vote" onClick={() => upVote(post)} /> 
        </div>
        
      </CardBody>
    </Card>
  )
}

export default PostItem;