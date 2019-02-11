import React from 'react';
import Post from './components/post'
import {
  Navbar,
  NavbarBrand,
  CardColumns,
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  FormGroup,
  Input
} from 'reactstrap';

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      posts: [],
      modal: false
    };
    this.toggle = this.toggle.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.upVote = this.upVote.bind(this);
    this.downVote = this.downVote.bind(this);
  }

  componentDidMount() {
    fetch('api/posts')
      .then(response => response.json())
      .then(data => this.setState({ posts: data }));
  }

  toggle() {
    this.setState({
      modal: !this.state.modal
    });
  }

  handleChange(event) {
    this.setState({ newText: event.target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
    fetch('api/posts/create', {
      method: 'POST',
      body: JSON.stringify({
        text: this.state.newText,
        votes: 0
      }),
      headers: { "Content-type": "application/json" }
    }).then(response => {
      if (response.status === 200) {
        fetch('api/posts')
          .then(response => response.json())
          .then(data => this.setState({ posts: data }));
        this.setState({ newText: '' });
        this.toggle()
      } else {
        alert('Houve um erro ao tentar salvar esse post, por favor tente novamente!');
      }
    });
  }

  upVote(post) {
    fetch(`/api/posts/${post.id}`, {
      method: 'PUT',
      body: JSON.stringify({
        id: post.id,
        text: post.text,
        votes: post.votes + 1,
      }),
      headers: { "Content-type": "application/json" }
    }).then(response => {
      if (response.status === 200) {
        fetch('api/posts')
          .then(response => response.json())
          .then(data => this.setState({ posts: data }));
      } else {
        alert('Houve um erro ao tentar salvar esse post, por favor tente novamente!');
      }
    });
  }

  downVote(post) {
    fetch(`/api/posts/${post.id}`, {
      method: 'PUT',
      body: JSON.stringify({
        id: post.id,
        text: post.text,
        votes: post.votes - 1,
      }),
      headers: { "Content-type": "application/json" }
    }).then(response => {
      if (response.status === 200) {
        fetch('api/posts')
          .then(response => response.json())
          .then(data => this.setState({ posts: data }));
      } else {
        alert('Houve um erro ao tentar salvar esse post, por favor tente novamente!');
      }
    });
  }

  render() {
    return (
      <div>
        <Navbar className="navbar" color="dark" dark >
          <NavbarBrand className="navbar-title" color="white" href="/">Segware Chalenge</NavbarBrand>
          <Button className="btn-adicionar" color="primary" onClick={this.toggle}>+ Adicionar</Button>
        </Navbar>

        <CardColumns>
          <Post 
            upVote={upVote => this.upVote(upVote) }
            downVote={downVote => this.downVote(downVote)}
            posts={this.state.posts} />
        </CardColumns>

        <Modal isOpen={this.state.modal} toggle={this.toggle} >
          <ModalHeader toggle={this.toggle}>Adicionar novo texto.</ModalHeader>
          <ModalBody>
            <FormGroup>
              <Input type="textarea" name="text" value={this.state.newText} onChange={this.handleChange} placeholder="Digite seu texto aqui..." />
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button color="secondary" onClick={this.toggle}>Cancelar</Button>{' '}
            <Button color="primary" onClick={this.handleSubmit}>Salvar</Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}